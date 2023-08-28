import { HTMLAttributes } from 'react';

function SelectOptionListContainer({
  children,
}: HTMLAttributes<HTMLDivElement>) {
  return (
    <div className="relative flex-grow">
      <div className="absolute top-0 bottom-0 w-full overflow-auto">
        <div className="flex flex-col items-center justify-center gap-12px px-32px">
          {children}
        </div>
      </div>
    </div>
  );
}

export default SelectOptionListContainer;
