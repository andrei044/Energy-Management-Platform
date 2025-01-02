# # Step 1: Use the Node image
# FROM node:17

# # Set the working directory in the container
# WORKDIR /app

# # Copy package files and install dependencies
# COPY package*.json ./
# RUN npm install

# # Copy the app files into the container
# COPY . .

# # Expose the port for local development
# EXPOSE 3000

# # Run the React app in development mode
# CMD ["npm", "start"]

# Step 1: Build React app
FROM node:17 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . ./
RUN npm run build

# Step 2: Serve with Nginx
FROM nginx:1.25
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Copy SSL certificate and private key
COPY ./ssl/nginx.crt /etc/ssl/certs/nginx.crt
COPY ./ssl/nginx.key /etc/ssl/private/nginx.key

EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]
