import { PropsWithChildren } from 'react';

interface SubOptionDescriptionProps {
  isActive: boolean;
}

function SubOptionDescription({
  children,
  isActive,
}: PropsWithChildren<SubOptionDescriptionProps>) {
  return (
    <div
      className={`${
        isActive ? 'text-grey-black' : 'text-grey-003'
      } body3 mt-8px`}
    >
      {children}
    </div>
  );
}

export default SubOptionDescription;
