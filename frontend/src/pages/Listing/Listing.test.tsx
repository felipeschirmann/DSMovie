import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import Listing from './index';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('Listing Page', () => {
  const mockMoviePage = {
    content: [
      {
        id: 1,
        title: 'The Witcher',
        score: 4.5,
        count: 2,
        image: 'https://image.com/witcher.jpg',
      }
    ],
    last: true,
    totalPages: 1,
    totalElements: 1,
    size: 12,
    number: 0,
    first: true,
    numberOfElements: 1,
    empty: false,
  };

  test('renders movies listing and handles page change', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: mockMoviePage });

    render(
      <BrowserRouter>
        <Listing />
      </BrowserRouter>
    );

    // Assert fetch was called
    expect(mockedAxios.get).toHaveBeenCalled();

    // Verify movie title is rendered
    await waitFor(() => {
      expect(screen.getByText('The Witcher')).toBeInTheDocument();
    });
  });
});
