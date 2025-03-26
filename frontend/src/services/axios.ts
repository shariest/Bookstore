import axios from 'axios'

const instance = axios.create({
  baseURL: `${location.origin.includes('localhost') ? 'http://localhost:8080' : location.origin}/api`,
  timeout: 10000
})

export default instance
