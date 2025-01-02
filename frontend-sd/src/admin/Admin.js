import React, { useEffect, useState } from 'react';
import { makeRequestToDevice, makeRequestToUser, ACTION_MESSAGE, ACTION_TYPING,ACTION_SEEN} from '../api/api';
import Chat from './Chat';
import {NotificationContainer, NotificationManager} from 'react-notifications';

const Admin = () => {
    const [devices, setDevices] = useState([]);
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // State for user fields
    const [userId, setUserId] = useState('');
    const [userName, setUserName] = useState('');
    const [userPassword, setUserPassword] = useState('');
    const [userRole, setUserRole] = useState('ROLE_CLIENT'); // Default role

    // State for device fields
    const [deviceId, setDeviceId] = useState('');
    const [deviceDescription, setDeviceDescription] = useState('');
    const [deviceAddress, setDeviceAddress] = useState('');
    const [deviceMaxEnergy, setDeviceMaxEnergy] = useState('');
    const [selectedUserIds, setSelectedUserIds] = useState([]); // New state for selected users

    // Function to fetch devices
    const fetchDevices = async () => {
        try {
            const data = await makeRequestToDevice('/device', 'GET'); // Adjust the endpoint as needed
            console.log(data);
            setDevices(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchUsers = async () => {
        const token = localStorage.getItem("token");
        try {
            const data = await makeRequestToUser('/user', 'GET'); 
            console.log(data);
            setUsers(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
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

    // Use useEffect to fetch devices and users on component mount
    useEffect(() => {
        fetchDevices();
        fetchUsers();
    }, []);

    // Handle Insert User
    const handleInsertUser = async () => {
        try {
            const newUser = {
                id: null,
                name: userName,
                password: userPassword,
                role: userRole,
            };
            await makeRequestToUser('/user', 'POST', newUser); // Adjust endpoint as needed
            // Optionally reset the fields
            setUserId('');
            setUserName('');
            setUserPassword('');
            setUserRole('ROLE_CLIENT');
            // Refetch users
            fetchUsers();
            alert('Success.');
        } catch (err) {
            setError(err.message);
            
        }
    };

    // Handle Update User
    const handleUpdateUser = async () => {
        try {
            const updatedUser = {
                id: userId,
                name: userName,
                password: userPassword,
                role: userRole,
            };
            await makeRequestToUser('/user', 'PUT', updatedUser); // Adjust endpoint as needed
            // Optionally reset the fields
            setUserId('');
            setUserName('');
            setUserPassword('');
            setUserRole('ROLE_CLIENT');
            // Refetch users
            fetchUsers();
            alert('Success.');
        } catch (err) {
            setError(err.message);
            
        }
    };

    // Handle Insert Device
    const handleInsertDevice = async () => {
        try {
            const newDevice = {
                id: null,
                description: deviceDescription,
                address: deviceAddress,
                maximumHourlyEnergyConsumption: parseInt(deviceMaxEnergy, 10),
                users: selectedUserIds.map(id => ({ id })), // Add selected user IDs
            };
            await makeRequestToDevice('/device', 'POST', newDevice);
            setDeviceId('');
            setDeviceDescription('');
            setDeviceAddress('');
            setDeviceMaxEnergy('');
            setSelectedUserIds([]);
            fetchDevices();
            alert('Device added successfully.');
        } catch (err) {
            setError(err.message);
        }
    };

    // Handle Update Device
    const handleUpdateDevice = async () => {
        try {
            const updatedDevice = {
                id: deviceId,
                description: deviceDescription,
                address: deviceAddress,
                maximumHourlyEnergyConsumption: parseInt(deviceMaxEnergy, 10),
                users: selectedUserIds.map(id => ({ id })), // Add selected user IDs
            };
            await makeRequestToDevice('/device', 'PUT', updatedDevice);
            setDeviceId('');
            setDeviceDescription('');
            setDeviceAddress('');
            setDeviceMaxEnergy('');
            setSelectedUserIds([]);
            fetchDevices();
            alert('Device updated successfully.');
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDeleteDevice = async () => {
        try {
            const id=deviceId
            await makeRequestToDevice(`/device/${id}`, 'DELETE');
            setDevices((prevDevices) => prevDevices.filter((device) => device.id !== id));
            alert('Device deleted successfully.');
        } catch (err) {
            setError(`Failed to delete device: ${err.message}`);
        }
    };
    const handleDeleteUser = async () => {
        try {
            const id=userId
            await makeRequestToUser(`/user/${id}`, 'DELETE');
            setUsers((prevUsers) => prevUsers.filter((user) => user.id !== id));
            alert('User deleted successfully.');
        } catch (err) {
            setError(`Failed to delete user: ${err.message}`);
        }
    };

    // Handle user selection for the device
    const handleUserSelection = (id) => {
        setSelectedUserIds((prev) =>
            prev.includes(id) ? prev.filter((userId) => userId !== id) : [...prev, id]
        );
    };

    
    // Render loading, error, or devices
    if (loading) return <div>Loading devices...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div>
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
            <h1>Users</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user) => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.role}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* User Input Fields */}
            <h2>Add / Update User</h2>
            <div>
                <input
                    type="text"
                    placeholder="User ID"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Name"
                    value={userName}
                    onChange={(e) => setUserName(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={userPassword}
                    onChange={(e) => setUserPassword(e.target.value)}
                />
                <select value={userRole} onChange={(e) => setUserRole(e.target.value)}>
                    <option value="ROLE_CLIENT">Client</option>
                    <option value="ROLE_ADMIN">Admin</option>
                </select>
                <button onClick={handleInsertUser}>Insert</button>
                <button onClick={handleUpdateUser}>Update</button>
                <button onClick={handleDeleteUser}>Delete</button>
            </div>
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
                                        <li key={user.id}>{user.id}</li>
                                    ))}
                                </ul>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <h2>Add / Update Device</h2>
            <div>
                <input type="text" placeholder="Device ID" value={deviceId} onChange={(e) => setDeviceId(e.target.value)} />
                <input type="text" placeholder="Description" value={deviceDescription} onChange={(e) => setDeviceDescription(e.target.value)} />
                <input type="text" placeholder="Address" value={deviceAddress} onChange={(e) => setDeviceAddress(e.target.value)} />
                <input type="number" placeholder="Max Hourly Energy" value={deviceMaxEnergy} onChange={(e) => setDeviceMaxEnergy(e.target.value)} />

                {/* User selection for device */}
                <div>
                    <h3>Select Users for Device</h3>
                    {users.map((user) => (
                        <label key={user.id}>
                            <input
                                type="checkbox"
                                value={user.id}
                                checked={selectedUserIds.includes(user.id)}
                                onChange={() => handleUserSelection(user.id)}
                            />
                            {user.name} ({user.id})
                        </label>
                    ))}
                </div>

                <button onClick={handleInsertDevice}>Insert Device</button>
                <button onClick={handleUpdateDevice}>Update Device</button>
                <button onClick={handleDeleteDevice}>Delete</button>
            </div>
            <h2>SUPPORT CHAT</h2>
            <Chat notifyFunc={notifyFunc} admin={true} ></Chat>
            <NotificationContainer/>
        </div>
    );
};

export default Admin;
