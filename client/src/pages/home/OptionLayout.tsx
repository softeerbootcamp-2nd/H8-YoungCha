import React from 'react';

interface OptionLayoutProps {
  title?: string;
  children?: React.ReactNode;
}

function OptionLayout({ title, children }: OptionLayoutProps) {
  return (
    <div className="flex flex-col gap-16px max-w-7xl">
      {title && (
        <h3 className="font-medium text-center text-grey-black py-8px">
          {title}
        </h3>
      )}
      <ul className="flex justify-around gap-48px w-1024px px-24px">
        {children}
      </ul>
    </div>
  );
}

export default OptionLayout;
