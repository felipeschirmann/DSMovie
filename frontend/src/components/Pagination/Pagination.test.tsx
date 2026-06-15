import { render, screen, fireEvent } from '@testing-library/react';
import Pagination from './index';
import { MoviePage } from 'types/movie';

describe('Pagination Component', () => {
  const mockOnChange = jest.fn();

  beforeEach(() => {
    mockOnChange.mockClear();
  });

  test('renders active page text and handles first page state correctly', () => {
    const mockPage: MoviePage = {
      content: [],
      last: false,
      totalPages: 5,
      totalElements: 50,
      size: 10,
      number: 0,
      first: true,
      numberOfElements: 0,
      empty: true,
    };

    render(<Pagination page={mockPage} onChange={mockOnChange} />);

    // Page number text should display "1 de 5" (page.number + 1)
    expect(screen.getByText('1 de 5')).toBeInTheDocument();

    // Verify left arrow button is disabled
    const buttons = screen.getAllByRole('button');
    const prevButton = buttons[0];
    const nextButton = buttons[1];

    expect(prevButton).toBeDisabled();
    expect(nextButton).not.toBeDisabled();

    // Clicking previous button should not trigger onChange since it's disabled
    fireEvent.click(prevButton);
    expect(mockOnChange).not.toHaveBeenCalled();

    // Clicking next button should trigger onChange with page number 1
    fireEvent.click(nextButton);
    expect(mockOnChange).toHaveBeenCalledWith(1);
  });

  test('handles last page state correctly and triggers page navigation', () => {
    const mockPage: MoviePage = {
      content: [],
      last: true,
      totalPages: 5,
      totalElements: 50,
      size: 10,
      number: 4,
      first: false,
      numberOfElements: 0,
      empty: true,
    };

    render(<Pagination page={mockPage} onChange={mockOnChange} />);

    // Page number text should display "5 de 5"
    expect(screen.getByText('5 de 5')).toBeInTheDocument();

    const buttons = screen.getAllByRole('button');
    const prevButton = buttons[0];
    const nextButton = buttons[1];

    expect(prevButton).not.toBeDisabled();
    expect(nextButton).toBeDisabled();

    // Clicking next button should not trigger onChange since it's disabled
    fireEvent.click(nextButton);
    expect(mockOnChange).not.toHaveBeenCalled();

    // Clicking previous button should trigger onChange with page number 3
    fireEvent.click(prevButton);
    expect(mockOnChange).toHaveBeenCalledWith(3);
  });
});
