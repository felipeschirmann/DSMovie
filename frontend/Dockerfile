FROM node:13-alpine
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json
RUN yarn install --silent
RUN yarn install react-scripts@3.3.1 -g --silent
CMD ["yarn", "start"]