import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import MovieCard from './index';
import { Movie } from 'types/movie';

describe('MovieCard Component', () => {
  const mockMovie: Movie = {
    id: 1,
    title: 'The Witcher',
    score: 4.5,
    count: 2,
    image: 'https://image.com/witcher.jpg',
  };

  test('renders movie image, title, score, and link to review form', () => {
    render(
      <BrowserRouter>
        <MovieCard movie={mockMovie} />
      </BrowserRouter>
    );

    // Title
    expect(screen.getByText('The Witcher')).toBeInTheDocument();

    // Image attributes
    const imgElement = screen.getByAltText('The Witcher');
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', 'https://image.com/witcher.jpg');

    // Score
    expect(screen.getByText('4.5')).toBeInTheDocument();
    expect(screen.getByText('2 avaliações')).toBeInTheDocument();

    // Review form link
    const linkElement = screen.getByText('Avaliar').closest('a');
    expect(linkElement).toBeInTheDocument();
    expect(linkElement).toHaveAttribute('href', '/form/1');
  });
});
