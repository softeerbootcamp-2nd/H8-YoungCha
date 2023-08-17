import { ReactNode } from 'react';

interface PopUpMainProps {
  title: string;
  imgSrc: ReactNode;
}

function PopUpMain({ title, imgSrc }: PopUpMainProps) {
  return (
    <div className="flex flex-col justify-center items-center title2">
      {imgSrc}
      <span className="mt-8px text-main-blue">{title}</span>
    </div>
  );
}

export default PopUpMain;
