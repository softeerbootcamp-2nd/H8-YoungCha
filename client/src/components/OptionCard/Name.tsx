import { PropsWithChildren } from 'react';

interface NameProps {
  isActive: boolean;
}

function Name({ children, isActive }: PropsWithChildren<NameProps>) {
  const nameTextColor = isActive ? 'text-grey-black' : 'text-grey-003';
  return (
    <div className={`${nameTextColor} font-medium text-20px mb-10px`}>
      {children}
    </div>
  );
}

export default Name;
