import React, { useEffect, useState, useRef } from 'react';
import { makeRequestToDevice, createWs, makeRequestToMonitoring, ACTION_MESSAGE, ACTION_TYPING,ACTION_SEEN  } from '../api/api';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';
import dayjs from "dayjs";
import { BarChart } from "@mui/x-charts/BarChart";
import { TextField, Box } from "@mui/material";
import Chat from '../admin/Chat';


const Client = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedDate, setSelectedDate] = useState(dayjs().format("YYYY-MM-DD"));
    const [chartData, setChartData] = useState([]);
    const wsRef = useRef([]);

    // Function to fetch devices
    const fetchDevices = async () => {
        try {
            const data = await makeRequestToDevice('/device/me','GET');
            console.log(data)
            // Open WebSocket connection
            for (let device of data){
                //const ws = new WebSocket(`ws://localhost:8082/ws/${device.id}`)
                const ws = createWs(device.id)
                wsRef.current.push(ws);

                // Event listeners
                ws.onopen = () => {
                console.log("Connected to WebSocket server");
                };

                ws.onmessage = (event) => {
                console.log("Message received:", event.data);
                NotificationManager.info(event.data);
                };

                ws.onclose = () => {
                console.log("Disconnected from WebSocket server");
                };

                ws.onerror = (error) => {
                console.error("WebSocket error:", error);
                };
            }
            setDevices(data);
            
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    // Fetch energy consumption data
    const fetchEnergyData = async () => {
        if (!devices.length) return;

        const startOfDay = dayjs(selectedDate).startOf("day").valueOf();
        console.log(startOfDay)
        const endOfDay = dayjs(selectedDate).endOf("day").valueOf();
        const hourlyData = new Array(24).fill(0);

        try {
            for (const device of devices) {
                const energyData = await makeRequestToMonitoring(`/measurement/${device.id}/${startOfDay}`,'GET');
                console.log(energyData)
                energyData.forEach((entry) => {
                    const hour = dayjs(entry.timestamp).hour(); // Get hour from timestamp
                    hourlyData[hour] += entry.energyConsumed; // Add energy consumed to the hour
                });
            }
            
            

            // Update chart data
            setChartData(hourlyData);
        }catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    // Use useEffect to fetch devices on component mount
    useEffect(() => {
        fetchDevices();
        return () => {
                wsRef.current.forEach((ws) => ws.close());
                wsRef.current = []; // Clear the reference array
                };
    }, []);

    // Fetch energy data whenever the date changes
    useEffect(() => {
        fetchEnergyData();
    }, [selectedDate, devices]);

    // Handle date selection change
    const handleDateChange = (event) => {
        setSelectedDate(event.target.value);
    };

    const notifyFunc = (msg) =>{
        if(msg.action==ACTION_MESSAGE){
            NotificationManager.info(`New message from ${msg.sender}`);
        }else if(msg.action==ACTION_SEEN){
            NotificationManager.info(`Message seen from ${msg.sender}`);
        }else if(msg.action==ACTION_TYPING){
            //nothing
        }
    }

    // Render loading, error, or devices
    if (loading) return <div>Loading devices...</div>;
    // if (error) return <div>Error: {error}</div>;
    
    return (
        
        <div>
            {error && <div>Error: {error}</div>}
            <h1>Devices</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Address</th>
                        <th>Max Hourly Energy Consumption</th>
                        <th>Users</th>
                    </tr>
                </thead>
                <tbody>
                    {devices.map((device) => (
                        <tr key={device.id}>
                            <td>{device.id}</td>
                            <td>{device.description}</td>
                            <td>{device.address}</td>
                            <td>{device.maximumHourlyEnergyConsumption}</td>
                            <td>
                                <ul>
                                    {device.users.map((user) => (
                                        <li key={user.id}>
                                            {user.id}
                                        </li>
                                    ))}
                                </ul>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <Box mb={3}>
                <TextField
                label="Select Date"
                type="date"
                value={selectedDate}
                onChange={handleDateChange}
                InputLabelProps={{ shrink: true }}
                />
            </Box>
            <BarChart
                xAxis={[
                {
                    id: "barCategories",
                    data: Array.from({ length: 24 }, (_, i) => String(i).padStart(2, "0")),
                    scaleType: "band",
                },
                ]}
                series={[
                {
                    data: chartData,
                    label: "Energy Consumption",
                },
                ]}
                width={500}
                height={300}
            />
            <h2>CHAT WITH ADMIN</h2>
            <Chat notifyFunc={notifyFunc} admin={false} ></Chat>
            <NotificationContainer/>
        </div>
    );
};

export default Client;