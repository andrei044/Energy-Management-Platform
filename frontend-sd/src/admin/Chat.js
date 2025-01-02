// import React, { useState, useEffect, useRef } from 'react';
// import { createChatWs } from '../api/api';
// import {
//   Avatar,
//   Badge,
//   Box,
//   IconButton,
//   List,
//   ListItem,
//   ListItemAvatar,
//   ListItemText,
//   TextField,
//   Typography,
// } from '@mui/material';
// import SendIcon from '@mui/icons-material/Send';
// import DoneIcon from '@mui/icons-material/Done';
// import DoneAllIcon from '@mui/icons-material/DoneAll';
// import CircleIcon from '@mui/icons-material/Circle';
// import { styled } from '@mui/system';
// import {NotificationContainer, NotificationManager} from 'react-notifications';

// // Styled components
// const TypingIndicator = styled('div')({
//   fontStyle: 'italic',
//   color: 'gray',
// });

// const ChatListItem = ({ chat, onSelect }) => (
//   <ListItem button onClick={() => onSelect(chat)}>
//     <ListItemAvatar>
//       <Badge
//         color="error"
//         variant="dot"
//         invisible={!chat.unread}
//         overlap="circular"
//       >
//         <Avatar>
//           {/* {chat.name[0]} */}

//         </Avatar>
//       </Badge>
//     </ListItemAvatar>
//     <ListItemText
//       primary={chat.name}
//       secondary={chat.lastMessage}
//       secondaryTypographyProps={{ style: { color: chat.unread ? 'black' : 'gray' } }}
//     />
//   </ListItem>
// );

// const Message = ({ text, date, seen }) => (
//   <Box display="flex" flexDirection="column" mb={1}>
//     <Typography variant="body1">{text}</Typography>
//     <Box display="flex" justifyContent="space-between" alignItems="center">
//       <Typography variant="caption" color="textSecondary">
//         {date}
//       </Typography>
//       {seen ? <DoneAllIcon fontSize="small" /> : <DoneIcon fontSize="small" />}
//     </Box>
//   </Box>
// );

// const Chat = ( {notifyFunc} ) => {
//   const [chats, setChats] = useState([
//     {
//       id: 1,
//       name: 'Alice',
//       lastMessage: 'See you tomorrow!',
//       unread: true,
//     },
//     {
//       id: 2,
//       name: 'Bob',
//       lastMessage: 'Thanks for the update!',
//       unread: false,
//     },
//   ]);

//   // state for chat fields
//   const wsRef = useRef();
//   const [typingStatus, setTypingStatus] = useState({}); // Typing status for each user

//   const [currentChat, setCurrentChat] = useState(null);
//   const [messages, setMessages] = useState([]);
//   const [messagesMap, setMessagesMap]=useState({});
//   const [newMessage, setNewMessage] = useState('');
//   const [typing, setTyping] = useState(false);

//   useEffect(() => {
//     // if (currentChat) {
//       // // Mock typing indicator for demonstration
//       // setTyping(true);
//       // setTimeout(() => setTyping(false), 2000);
//       // // setMessages((prev) => [
//       // //   ...prev,
//       // //   {text:"123",date:"1992-10-10",seen:true},
//       // // ]);
//     // }
//   }, [currentChat]);

//   const handleSendMessage = () => {
//     if (newMessage.trim()) {
//       setMessages((prev) => [
//         ...prev,
//         { text: newMessage, date: new Date().toLocaleTimeString(), seen: false },
//       ]);
//       setNewMessage('');
//     }
//   };

//   const handleWsOpen=()=>{
//     const ws = createChatWs()
//     wsRef.current=ws;

//     // Event listeners
//     ws.onopen = () => {
//         console.log("Connected to WebSocket server");
//     };

//     ws.onmessage = (event) => {
//         console.log("Message received:", event.data);
//         // const msg=JSON.parse(event.data) // {sender:"",content:"",receiver:"",action:""}
//         // //action can be SEEN, MESSAGE, TYPING
//         // NotificationManager.info(`Received message from: ${msg.sender}`);
//         const msg = JSON.parse(event.data); // { sender, content, receiver, action }

//         notifyFunc(msg.sender)

//         if (msg.action === "MESSAGE") {
//             // Update chat list and current chat
//             setChats((prevChats) => {
//                 const updatedChats = [...prevChats];
//                 const chatIndex = updatedChats.findIndex((chat) => chat.sender === msg.sender);
                
//                 if (chatIndex > -1) {
//                     // Add the message to the existing chat
//                     updatedChats[chatIndex].messages.push({ content: msg.content, seen: false });
//                 } else {
//                     // New chat
//                     updatedChats.push({
//                         sender: msg.sender,
//                         avatar: "/path/to/default/avatar.png", // Replace with appropriate avatar URL
//                         messages: [{ content: msg.content, seen: false }],
//                         unread: true,
//                     });
//                 }
//                 return updatedChats;
//             });

//             // // Notify user if it's not the active chat
//             // if (!currentChat || currentChat.sender !== msg.sender) {
//                 // NotificationManager.info(`New message from ${msg.sender}`);
//                 // notifyFunc(msg.sender)
//             // }
//         } else if (msg.action === "SEEN") {
//             // Mark messages as seen
//             setChats((prevChats) => {
//                 return prevChats.map((chat) => {
//                     if (chat.sender === msg.sender) {
//                         return {
//                             ...chat,
//                             messages: chat.messages.map((message) => ({ ...message, seen: true })),
//                         };
//                     }
//                     return chat;
//                 });
//             });
//         } else if (msg.action === "TYPING") {
//             // Update typing status
//             setTypingStatus((prevStatus) => ({
//                 ...prevStatus,
//                 [msg.sender]: true,
//             }));

//             // Reset typing status after a delay
//             setTimeout(() => {
//                 setTypingStatus((prevStatus) => ({
//                     ...prevStatus,
//                     [msg.sender]: false,
//                 }));
//             }, 3000); // Adjust delay as needed
//         }
//     };

//     ws.onclose = () => {
//         console.log("Disconnected from WebSocket server");
//     };

//     ws.onerror = (error) => {
//         console.error("WebSocket error:", error);
//     };
// }


//   useEffect(()=>{
//     handleWsOpen();
//     return () => {
//         wsRef.current.close();
//         };
//   },[]);

//   return (
//     <Box display="flex" height="100vh">
//       {/* Chat List */}
//       <Box width="30%" borderRight="1px solid #ddd" overflow="auto">
//         <List>
//           {chats.map((chat) => (
//             <ChatListItem
//               key={chat.id}
//               chat={chat}
//               onSelect={(chat) => {
//                 setCurrentChat(chat);
//                 chat.unread = false; // Mark chat as read
//                 setChats([...chats]);
//               }}
//             />
//           ))}
//         </List>
//       </Box>

//       {/* Chat Window */}
//       <Box width="70%" display="flex" flexDirection="column" p={2}>
//         {currentChat ? (
//           <>
//             <Typography variant="h6" mb={2}>
//               Chat with {currentChat.name}
//             </Typography>
//             <Box flexGrow={1} overflow="auto" mb={2}>
//               {messages.map((msg, index) => (
//                 <Message key={index} text={msg.text} date={msg.date} seen={msg.seen} />
//               ))}
//               {typing && <TypingIndicator>{currentChat.name} is typing...</TypingIndicator>}
//             </Box>
//             <Box display="flex" alignItems="center">
//               <TextField
//                 fullWidth
//                 placeholder="Type a message"
//                 value={newMessage}
//                 onChange={(e) => setNewMessage(e.target.value)}
//               />
//               <IconButton color="primary" onClick={handleSendMessage}>
//                 <SendIcon />
//               </IconButton>
//             </Box>
//           </>
//         ) : (
//           <Typography variant="h6" color="textSecondary">
//             Select a chat to start messaging
//           </Typography>
//         )}
//       </Box>
//       {/* <NotificationContainer/> */}
//     </Box>
//   );
// };

// export default Chat;
import React, { useState, useEffect, useRef } from 'react';
import { createChatWs, ACTION_MESSAGE, ACTION_TYPING,ACTION_SEEN } from '../api/api';
import {
  Avatar,
  Badge,
  Box,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  TextField,
  Typography,
} from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import DoneIcon from '@mui/icons-material/Done';
import DoneAllIcon from '@mui/icons-material/DoneAll';
import CircleIcon from '@mui/icons-material/Circle';
import { styled } from '@mui/system';
import { NotificationManager } from 'react-notifications';

const TypingIndicator = styled('div')({
  fontStyle: 'italic',
  color: 'gray',
});



const ChatListItem = ({ chat, onSelect }) => (
  <ListItem button onClick={() => onSelect(chat)}>
    <ListItemAvatar>
      <Badge
        color="error"
        variant="dot"
        invisible={!chat.unread}
        overlap="circular"
      >
        <Avatar />
      </Badge>
    </ListItemAvatar>
    <ListItemText
      primary={chat.name}
      secondary={chat.lastMessage}
      secondaryTypographyProps={{ style: { color: chat.unread ? 'black' : 'gray' } }}
    />
  </ListItem>
);

const Message = ({ text, date, seen, sender}) => (
  <Box display="flex" flexDirection="column" mb={1}>
    <Typography variant="body1">{text}</Typography>
    <Box display="flex" justifyContent="space-between" alignItems="center">
      <Typography variant="caption" color="textSecondary">
        {sender+" "+date}
      </Typography>
      {seen ? <DoneAllIcon fontSize="small" /> : <DoneIcon fontSize="small" />}
    </Box>
  </Box>
);

const Chat = ({ notifyFunc, admin }) => {
  const [chats, setChats] = useState([
    // {
    //   id: 1,
    //   name: 'Alice',
    //   lastMessage: 'See you tomorrow!',
    //   unread: true,
    // },
    // {
    //   id: 2,
    //   name: 'Bob',
    //   lastMessage: 'Thanks for the update!',
    //   unread: false,
    // },
  ]);

  const wsRef = useRef();
  const [typingStatus, setTypingStatus] = useState({});
  const [currentChat, setCurrentChat] = useState(null);
  const currentChatRef = useRef(currentChat)
  const [messagesMap, setMessagesMap] = useState({});
  const [newMessage, setNewMessage] = useState('');
  const [userToChat, setUserToChat] = useState('');

  useEffect(() => {
    currentChatRef.current = currentChat; // Update the ref whenever currentChat changes
  }, [currentChat]);

  useEffect(() => {
    const ws = createChatWs();
    wsRef.current = ws;

    ws.onopen = () => {
      console.log('Connected to WebSocket server');
    };

    ws.onmessage = (event) => {
      // event.preventDefault()
      // console.log(event.data)
      // console.log(currentChatRef.current)
      const msg = JSON.parse(event.data); // { sender, content, receiver, action }
      // notifyFunc(msg.sender);
      if(msg.sender === localStorage.getItem("username")){
        return;
      }

      if (msg.action === 'MESSAGE') {
        setMessagesMap((prevMap) => {
          const updatedMap = { ...prevMap };
          if (!updatedMap[msg.sender]) {
            updatedMap[msg.sender] = [];
          }
          updatedMap[msg.sender].push({
            text: msg.content,
            sender:msg.sender,
            date: new Date().toLocaleTimeString(),
            seen: currentChatRef.current && currentChatRef.current.name === msg.sender ? true : false,
          });
          return updatedMap;
        });


        if(currentChatRef.current && currentChatRef.current.name === msg.sender){
          wsRef.current.send(JSON.stringify({
            content: "",
            sender: localStorage.getItem("username"),
            receiver: currentChatRef.current.name,
            action: ACTION_SEEN,
          }))
        }

        setChats((prevChats) => {
          const updatedChats = prevChats.map((chat) =>{
            return chat.name === msg.sender
              ? { ...chat, lastMessage: msg.content, unread: true }
              : chat
          }
        );

          if (!updatedChats.some((chat) => chat.name === msg.sender )) {
            updatedChats.push({
              id: Date.now(),
              name: msg.sender,
              lastMessage: msg.content,
              unread: true,
            });
          }

          return updatedChats;
        });

        // Notify user if it's not the active chat
        if (!currentChatRef.current || currentChatRef.current.name !== msg.sender) {
            notifyFunc(msg)
        }
      } else if (msg.action === 'SEEN') {
        setMessagesMap((prevMap) => {
          const updatedMap = { ...prevMap };

          if (updatedMap[msg.sender]) {
            updatedMap[msg.sender] = updatedMap[msg.sender].map((message) => ({
              ...message,
              seen: message.sender === localStorage.getItem("username") ? true : message.seen,
            }));
          }
          // console.log(updatedMap)
          // setCurrentChat(updatedMap[msg.sender])
          return updatedMap;
        });
        if(!currentChatRef.current || currentChatRef.current.name !==msg.name){
          notifyFunc(msg)
        }
        
      } else if (msg.action === 'TYPING') {
        setTypingStatus((prevStatus) => ({
          ...prevStatus,
          [msg.sender]: true,
        }));

        setTimeout(() => {
          setTypingStatus((prevStatus) => ({
            ...prevStatus,
            [msg.sender]: false,
          }));
        }, 3000);
      }
    };

    ws.onclose = () => {
      console.log('Disconnected from WebSocket server');
    };

    ws.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    return () => {
      ws.close();
    };
  }, []);

  const handleSendMessage = () => {
    console.log(currentChat)
    if (newMessage.trim() && currentChat) {
      setMessagesMap((prevMap) => {
        const updatedMap = { ...prevMap };
        if (!updatedMap[currentChat.name]) {
          updatedMap[currentChat.name] = [];
        }
        updatedMap[currentChat.name].push({
          text: newMessage,
          date: new Date().toLocaleTimeString(),
          sender: localStorage.getItem("username"),
          seen: false,
        });
        // setCurrentChat(updatedMap[currentChat.name])
        return updatedMap;
      });
      wsRef.current.send(JSON.stringify({
        content: newMessage,
        sender: localStorage.getItem("username"),
        receiver: currentChat.name,
        action: ACTION_MESSAGE,
      }))
      setNewMessage('');
    }
  };

  const handleAddChat = () =>{
    const addedChat={
      id: Date.now(),
          name: userToChat,
          lastMessage: "",
          unread: false,
    }
    setChats((prevChats) => {
      if (!prevChats.some((chat) => chat.name === userToChat )) {
        prevChats.push(addedChat);
      }
      // console.log(prevChats)
      return prevChats;
    });
    setCurrentChat(addedChat)
  }

  const handleAddAdminChat = () =>{
    const addedChat={
      id: Date.now(),
          name: "admin",
          lastMessage: "",
          unread: false,
    }
    setChats((prevChats) => {
      if (!prevChats.some((chat) => chat.name === userToChat )) {
        prevChats.push(addedChat);
      }
      return prevChats;
    });
    setCurrentChat(addedChat)
  }

  return (
    <Box display="flex" height="100vh">
      <Box width="30%" borderRight="1px solid #ddd" overflow="auto">
      <Box display="flex" alignItems="center">
              {admin && <><TextField placeholder='Type user to chat' onChange={(e) => { setUserToChat(e.target.value); } } /><IconButton color="primary" onClick={handleAddChat}>
            <SendIcon />
          </IconButton></>}
            {!admin && <>
              <button onClick={handleAddAdminChat}>Support chat</button>
            
            </>}
            </Box>
        <List>
          {chats.map((chat) => (
            <ChatListItem
              key={chat.id}
              chat={chat}
              onSelect={(chat) => {
                
                // if(messagesMap[chat.name] && messagesMap[chat.name].some((msg)=>msg.seen === false)){
                  // console.log()
                  if(messagesMap[chat.name] && messagesMap[chat.name].some((msg)=>msg.sender !== localStorage.getItem("username") && msg.seen === false)){
                    wsRef.current.send(JSON.stringify({
                      content: "",
                      sender: localStorage.getItem("username"),
                      receiver: chat.name,
                      action: ACTION_SEEN,
                    }))
                  }
                  setMessagesMap((prevMap) => {
                    const updatedMap = { ...prevMap };
          
                    if (updatedMap[chat.name]) {
                      updatedMap[chat.name] = updatedMap[chat.name].map((message) => ({
                        ...message,
                        seen: message.sender !== localStorage.getItem("username") ? true : message.seen,
                      }));
                    }
                    // console.log(updatedMap)
                    // setCurrentChat(updatedMap[msg.sender])
                    return updatedMap;
                  });

                  
                // }
                
                setChats((prevChats) =>
                  prevChats.map((c) =>
                    c.name === chat.name ? { ...c, unread: false } : c
                  )
                );
                setCurrentChat(chat);
              }}
            />
          ))}
        </List>
      </Box>

      <Box width="70%" display="flex" flexDirection="column" p={2}>
        {currentChat ? (
          <>
            <Typography variant="h6" mb={2}>
              Chat with {currentChat.name}
            </Typography>
            <Box flexGrow={1} overflow="auto" mb={2}>
              {messagesMap[currentChat.name]?.map((msg, index) => (
                <Message key={index} text={msg.text} date={msg.date} seen={msg.seen} sender={msg.sender}/>
              ))}
              {typingStatus[currentChat.name] && (
                <TypingIndicator>{currentChat.name} is typing...</TypingIndicator>
              )}
            </Box>
            <Box display="flex" alignItems="center">
              <TextField
                fullWidth
                placeholder="Type a message"
                value={newMessage}
                onChange={(e) =>{ 
                  setNewMessage(e.target.value)
                  // console.log(currentChat)
                  wsRef.current.send(JSON.stringify({
                    content: "",
                    sender: localStorage.getItem("username"),
                    receiver: currentChat.name,
                    action: ACTION_TYPING,
                  }))
                }}
              />
              <IconButton color="primary" onClick={handleSendMessage}>
                <SendIcon />
              </IconButton>
            </Box>
          </>
        ) : (
          <Typography variant="h6" color="textSecondary">
            Select a chat to start messaging
          </Typography>
        )}
      </Box>
    </Box>
  );
};

export default Chat;
