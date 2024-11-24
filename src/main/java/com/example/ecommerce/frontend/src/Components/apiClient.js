import axios from "axios";
import { jwtDecode } from "jwt-decode"; // Use jwt-decode library

const apiClient = axios.create({
  baseURL: "http://localhost:8080",
});

let isRefreshing = false;
let refreshSubscribers = [];

const subscribeTokenRefresh = (cb) => {
  refreshSubscribers.push(cb);
};

const onRefreshed = (newAccessToken) => {
  refreshSubscribers.forEach((cb) => cb(newAccessToken));
  refreshSubscribers = [];
};

// Attach interceptor
apiClient.interceptors.request.use(
  async (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (!accessToken) return config;

    const decodedToken = jwtDecode(accessToken);
    const currentTime = Date.now() / 1000;

    // Check if token will expire in the next 30 seconds
    if (decodedToken.exp - currentTime < 30) {
      if (!isRefreshing) {
        isRefreshing = true;

        try {
          const refreshToken = localStorage.getItem("refreshToken");
          const response = await axios.post(`${config.baseURL}/auth/refresh`, { refreshToken });

          const { accessToken: newAccessToken, refreshToken: newRefreshToken } = response.data;
          localStorage.setItem("accessToken", newAccessToken);
          localStorage.setItem("refreshToken", newRefreshToken);

          isRefreshing = false;
          onRefreshed(newAccessToken);

          // Reload the current page to maintain the user's context
          window.location.reload();
        } catch (err) {
          isRefreshing = false;
          // Store the current page URL to redirect back after re-login
          const currentPath = window.location.pathname;
          localStorage.setItem("redirectAfterLogin", currentPath);

          localStorage.clear();
          window.location.href = "/"; // Redirect to login
          return Promise.reject(err);
        }
      }

      // Queue requests until refresh completes
      return new Promise((resolve) => {
        subscribeTokenRefresh((newAccessToken) => {
          config.headers["Authorization"] = `Bearer ${newAccessToken}`;
          resolve(config);
        });
      });
    }

    // Set Authorization header
    config.headers["Authorization"] = `Bearer ${accessToken}`;
    return config;
  },
  (error) => Promise.reject(error)
);

// Handle redirect after login
window.addEventListener("load", () => {
  const redirectPath = localStorage.getItem("redirectAfterLogin");
  if (redirectPath) {
    localStorage.removeItem("redirectAfterLogin");
    window.location.href = redirectPath;
  }
});

export default apiClient;
