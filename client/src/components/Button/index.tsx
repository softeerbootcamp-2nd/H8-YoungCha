import { ReactNode } from 'react';

interface ButtonProps {
  children: ReactNode;
}

function Button({ children }: ButtonProps) {
  return <button>{children}</button>;
}

export default Button;
