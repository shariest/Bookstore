import axios from '@/services/axios'
import type { Books, Params, ResultSet } from '@/types'

const BASE_URL = '/books'

export const createBook = async (book: Books): Promise<Books> => {
  try {
    const response = await axios.post(BASE_URL, book)
    return response.data
  } catch (error) {
    console.error('Error adding book:', error)
    throw error
  }
}

export const getBook = async (bid: number): Promise<Books> => {
  try {
    const response = await axios.get<Books>(`${BASE_URL}/${bid}`)
    return response.data
  } catch (error) {
    console.error('Error fetching books:', error)
    throw error
  }
}

/**
 *
 * @param params
 * @returns
 */
export const getBooks = async (params: Params): Promise<ResultSet<Books>> => {
  try {
    const response = await axios.get<ResultSet<Books>>(BASE_URL, {
      params
    })
    return response.data
  } catch (error) {
    console.error('Error fetching books:', error)
    throw error
  }
}

export const updateBook = async (bid: number, book: Books): Promise<Books> => {
  try {
    const response = await axios.put(`${BASE_URL}/${bid}`, book)
    return response.data
  } catch (error) {
    console.error('Error updating book:', error)
    throw error
  }
}

export const deleteBook = async (bid: number): Promise<void> => {
  try {
    const response = await axios.delete(`${BASE_URL}/${bid}`)
    return response.data
  } catch (error) {
    console.error('Error deleting book:', error)
    throw error
  }
}
