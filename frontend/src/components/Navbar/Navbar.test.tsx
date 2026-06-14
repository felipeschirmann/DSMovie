import { render, screen } from '@testing-library/react';
import Navbar from './index';

describe('Navbar Component', () => {
  test('renders logo text and GitHub contact link correctly', () => {
    render(<Navbar />);

    // Check if the DSMovie logo link exists and points to /
    const logoLink = screen.getByText('DSMovie');
    expect(logoLink).toBeInTheDocument();
    expect(logoLink.closest('a')).toHaveAttribute('href', '/');

    // Check if the contact link exists and points to the GitHub profile
    const contactLink = screen.getByText('/felipeschirmann');
    expect(contactLink).toBeInTheDocument();
    expect(contactLink.closest('a')).toHaveAttribute('href', 'https://github.com/felipeschirmann');
    expect(contactLink.closest('a')).toHaveAttribute('target', '_blank');
  });
});
