import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom'; // Import useParams to get URL params
import { makeRequestToDevice } from '../api/api';

const ClientPage = () => {
    const { id } = useParams(); // Get the client id from URL
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Function to fetch devices for a specific user by ID
    const fetchDevices = async () => {
        try {
            const data = await makeRequestToDevice(`/device/user/${id}`, 'GET');
            console.log(data);
            setDevices(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    // Fetch devices on component mount
    useEffect(() => {
        fetchDevices();
    }, [id]);

    // Render loading, error, or devices
    if (loading) return <div>Loading devices...</div>;
    // if (error) return <div>Error: {error}</div>;

    return (
        <div>
            <h1>Devices for User {id}</h1>
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
                                        <li key={user.id}>{user.id}</li>
                                    ))}
                                </ul>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ClientPage;