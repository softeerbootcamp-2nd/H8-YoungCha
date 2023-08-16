import { ReactNode } from 'react';
import PopUpMain from './PopUpMain';
import Description from './Contents/Description';
import PopUpButton from './Contents/PopUpButton';
import DualMufflerImg from './Contents/DualMufflerImg';
import SingleDescription from './Contents/SingleDescription';
import ModeSelectCard from './Contents/ModeSelectCard';

interface PopUpProps {
  onClose: () => void;
  children?: ReactNode;
}

function PopUp({ onClose, children }: PopUpProps) {
  return (
    <>
      <div
        className="fixed w-full h-full top-0 bottom-0 left-0 right-0 bg-black/80"
        onClick={onClose}
        role="none"
      ></div>
      <div className="w-343px h-fit bg-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-6px px-17px pt-26px pb-18px opacity-100">
        {children}
      </div>
    </>
  );
}

export default Object.assign(PopUp, {
  PopUpMain,
  SingleDescription,
  Description,
  PopUpButton,
  DualMufflerImg,
  ModeSelectCard,
});
