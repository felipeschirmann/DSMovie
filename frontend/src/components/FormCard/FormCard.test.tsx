import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import FormCard from './index';
import axios from 'axios';

const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));

jest.mock('axios');
const mockedAxios = axios as jest.MockedFunction<typeof axios> & { get: jest.Mock };

describe('FormCard Component', () => {
  const mockMovie = {
    id: 1,
    title: 'The Witcher',
    score: 4.5,
    count: 2,
    image: 'https://image.com/witcher.jpg',
  };

  beforeEach(() => {
    jest.clearAllMocks();
    mockedAxios.get.mockResolvedValue({ data: mockMovie } as any);
    mockedAxios.mockResolvedValue({ data: {} } as any);
  });

  test('loads and renders movie details, then submits review successfully', async () => {
    let container: HTMLElement = null as any;
    await act(async () => {
      const renderResult = render(
        <BrowserRouter>
          <FormCard movieId="1" />
        </BrowserRouter>
      );
      container = renderResult.container;
    });

    // Verify GET request was made on mount
    expect(mockedAxios.get).toHaveBeenCalledWith(expect.stringContaining('/movies/1'));

    // Wait for the movie details to load and display
    await waitFor(() => {
      expect(screen.getByText('The Witcher')).toBeInTheDocument();
    });

    const imgElement = screen.getByAltText('The Witcher');
    expect(imgElement).toHaveAttribute('src', 'https://image.com/witcher.jpg');

    // Find input fields
    const emailInput = screen.getByLabelText(/Informe seu email/i);
    const scoreSelect = screen.getByLabelText(/Informe sua avaliação/i);
    const saveButton = screen.getByRole('button', { name: /Salvar/i });

    // Mock form properties for JSDOM compatibility
    const formElement = container.querySelector('form');
    if (formElement) {
      Object.defineProperty(formElement, 'email', { value: emailInput, configurable: true });
      Object.defineProperty(formElement, 'score', { value: scoreSelect, configurable: true });
    }

    // Fill form with invalid email (should not submit)
    fireEvent.change(emailInput, { target: { value: 'invalid-email' } });
    fireEvent.click(saveButton);
    expect(mockedAxios).not.toHaveBeenCalled();

    // Fill form with valid email
    fireEvent.change(emailInput, { target: { value: 'maria@gmail.com' } });
    fireEvent.change(scoreSelect, { target: { value: '5' } });

    // Submit form
    fireEvent.click(saveButton);

    // Verify axios submit PUT score request
    await waitFor(() => {
      expect(mockedAxios).toHaveBeenCalledWith(expect.objectContaining({
        method: 'PUT',
        url: '/scores',
        data: {
          email: 'maria@gmail.com',
          movieId: '1',
          score: '5',
        },
      }));
    });

    // Verify navigation back to home page "/"
    expect(mockNavigate).toHaveBeenCalledWith('/');
  });

  test('clicking Cancel returns to home page', async () => {
    await act(async () => {
      render(
        <BrowserRouter>
          <FormCard movieId="1" />
        </BrowserRouter>
      );
    });

    const cancelButton = screen.getByRole('button', { name: /Cancelar/i });
    expect(cancelButton.closest('a')).toHaveAttribute('href', '/');
  });
});
