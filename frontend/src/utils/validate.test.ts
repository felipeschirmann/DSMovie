import { validateEmail } from './validate';

describe('validateEmail tests', () => {
  test('should return truthy for valid email addresses', () => {
    expect(validateEmail('maria@gmail.com')).toBeTruthy();
    expect(validateEmail('alex.blue@company.co.uk')).toBeTruthy();
  });

  test('should return falsy for invalid email addresses', () => {
    expect(validateEmail('maria')).toBeFalsy();
    expect(validateEmail('maria@')).toBeFalsy();
    expect(validateEmail('@gmail.com')).toBeFalsy();
    expect(validateEmail('maria@gmail')).toBeFalsy();
    expect(validateEmail('')).toBeFalsy();
    expect(validateEmail(null)).toBeFalsy();
    expect(validateEmail(undefined)).toBeFalsy();
  });
});
