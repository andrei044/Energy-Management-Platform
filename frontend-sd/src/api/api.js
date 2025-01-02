import axios from 'axios';
import SockJS from 'sockjs-client';

// Set the default HOST_USER environment variable
// const HOST_USER = process.env.REACT_APP_HOST_USER || 'http://localhost:8080';
const HOST_USER = process.env.REACT_APP_HOST_USER;
const HOST_DEVICE= process.env.REACT_APP_HOST_DEVICE;
const HOST_WS_MONITORING=process.env.REACT_APP_WS_MONITORING
const HOST_MONITORING= process.env.REACT_APP_HOST_MONITORING;
const HOST_WS_CHAT=process.env.REACT_APP_HOST_WS_CHAT

export const ACTION_MESSAGE="MESSAGE";
export const ACTION_TYPING="TYPING";
export const ACTION_SEEN="SEEN";

/**
 * Generic function to make HTTP requests.
 * @param {string} endpoint - The endpoint to which the request will be made.
 * @param {string} method - The HTTP method ('GET', 'POST', 'PUT', 'DELETE', etc.).
 * @param {Object} [data={}] - The request body data (for POST, PUT requests).
 * @param {Object} [headers={}] - Optional headers.
 * @returns {Promise<Object>} - The response data or error.
 */
export const makeRequestToUser = async (endpoint, method = 'GET', data = {}, headers = {}) => {
   // Retrieve the token from localStorage
   const token = localStorage.getItem("token");

   // Add the Authorization header dynamically
   const authHeaders = {
     ...headers,
     Authorization: token ? `Bearer ${token}` : undefined,
   };
  
  try {
    const response = await axios({
      url: `${HOST_USER}${endpoint}`,
      method,
      data,
      headers:authHeaders,
      withCredentials: true,  // Set to true if you need to send cookies with requests
    
    });
    // if(response.status==302){
    //     console.log(302)
    // }
    return response.data
  } catch (error) {
    console.error(`Request error: ${error}`);
    throw error; // Optionally handle errors or rethrow
  }
};

export const makeRequestToDevice = async (endpoint, method = 'GET', data = {}, headers = {}) => {
    // Retrieve the token from localStorage
    const token = localStorage.getItem("token");

    // Add the Authorization header dynamically
    const authHeaders = {
      ...headers,
      Authorization: token ? `Bearer ${token}` : undefined,
    };

    try {
      const response = await axios({
        url: `${HOST_DEVICE}${endpoint}`,
        method,
        data,
        headers:authHeaders,
        withCredentials: true,  // Set to true if you need to send cookies with requests
      
      });
    
      return response.data
    } catch (error) {
      console.error(`Request error: ${error}`);
      throw error; // Optionally handle errors or rethrow
    }
  };

  export const makeRequestToMonitoring = async (endpoint, method = 'GET', data = {}, headers = {}) => {
    // Retrieve the token from localStorage
    const token = localStorage.getItem("token");

    // Add the Authorization header dynamically
    const authHeaders = {
      ...headers,
      Authorization: token ? `Bearer ${token}` : undefined,
    };

    try {
      const response = await axios({
        url: `${HOST_MONITORING}${endpoint}`,
        method,
        data,
        headers: authHeaders,
      });
    
      return response.data
    } catch (error) {
      console.error(`Request error: ${error}`);
      throw error; // Optionally handle errors or rethrow
    }
  };

export function createWs(deviceId){
  const token = localStorage.getItem("token");
  return new WebSocket(`${HOST_WS_MONITORING}${deviceId}?token=${token}`);
}

export function createChatWs(){
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");
  return new WebSocket(`${HOST_WS_CHAT}${username}?token=${token}`);
}