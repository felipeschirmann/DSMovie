import { render, screen } from '@testing-library/react';
import MovieScore from './index';

describe('MovieScore Component', () => {
  test('renders score value and evaluation count correctly when score is positive', () => {
    render(<MovieScore score={4.5} count={12} />);

    // Display formatted score
    expect(screen.getByText('4.5')).toBeInTheDocument();

    // Display evaluation count
    expect(screen.getByText('12 avaliações')).toBeInTheDocument();
  });

  test('renders dash and zero evaluations correctly when score is zero or negative', () => {
    render(<MovieScore score={0} count={0} />);

    // Display "-" for score zero
    expect(screen.getByText('-')).toBeInTheDocument();

    // Display "0 avaliações"
    expect(screen.getByText('0 avaliações')).toBeInTheDocument();
  });
});
