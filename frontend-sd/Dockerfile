# Step 1: Use the Node image
FROM node:17

# Set the working directory in the container
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the app files into the container
COPY . .

# Expose the port for local development
EXPOSE 3000

# Run the React app in development mode
CMD ["npm", "start"]