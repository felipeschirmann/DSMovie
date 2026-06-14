import { render, screen } from '@testing-library/react';
import MovieStars from './index';

// Mock the SVGs to return custom divs with data-testid attributes
jest.mock('assets/img/star-full.svg', () => ({
  ReactComponent: () => <div data-testid="star-full" />
}));

jest.mock('assets/img/star-half.svg', () => ({
  ReactComponent: () => <div data-testid="star-half" />
}));

jest.mock('assets/img/star-empty.svg', () => ({
  ReactComponent: () => <div data-testid="star-empty" />
}));

describe('MovieStars Component', () => {
  test('renders 5 empty stars for score 0', () => {
    render(<MovieStars score={0} />);
    expect(screen.queryAllByTestId('star-full').length).toBe(0);
    expect(screen.queryAllByTestId('star-half').length).toBe(0);
    expect(screen.queryAllByTestId('star-empty').length).toBe(5);
  });

  test('renders 3 full, 1 half, and 1 empty star for score 3.5', () => {
    render(<MovieStars score={3.5} />);
    expect(screen.queryAllByTestId('star-full').length).toBe(3);
    expect(screen.queryAllByTestId('star-half').length).toBe(1);
    expect(screen.queryAllByTestId('star-empty').length).toBe(1);
  });

  test('renders 4 full, 1 half, and 0 empty stars for score 4.1', () => {
    render(<MovieStars score={4.1} />);
    expect(screen.queryAllByTestId('star-full').length).toBe(4);
    expect(screen.queryAllByTestId('star-half').length).toBe(1);
    expect(screen.queryAllByTestId('star-empty').length).toBe(0);
  });

  test('renders 5 full stars for score 5', () => {
    render(<MovieStars score={5} />);
    expect(screen.queryAllByTestId('star-full').length).toBe(5);
    expect(screen.queryAllByTestId('star-half').length).toBe(0);
    expect(screen.queryAllByTestId('star-empty').length).toBe(0);
  });
});
