import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { store } from './Store/store';
import { BrowserRouter } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';

const root = ReactDOM.createRoot(document.getElementById('root'));
const clientId="961098686120-p4pulrerjjelk6bhsbegd84m4r4mbdk7.apps.googleusercontent.com"
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
      <GoogleOAuthProvider clientId={clientId}> <App /> </GoogleOAuthProvider>;
       
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
);

reportWebVitals();
