import type { Page } from '@/types/page'

export interface ResultSet<T> {
  content: T[];
  page: Page;
}
