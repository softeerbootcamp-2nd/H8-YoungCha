import { ReactNode } from 'react';
import PopUpMain from './PopUpMain';
import CenteredDescription from './Contents/CenteredDescription';
import PopUpButton from './Contents/PopUpButton';
import DualMufflerImg from './Contents/DualMufflerImg';
import ModeSelectCard from './Contents/ModeSelectCard';
import ModelSelectCard from './Contents/ModelSelectCard';
import ModelCarousel from './Contents/ModelCarousel';
import PopUpProgressBar from './Contents/PopUpProgressBar';

interface PopUpProps {
  onClose?: () => void;
  children?: ReactNode;
}

function PopUp({ onClose, children }: PopUpProps) {
  return (
    <>
      <div
        className="fixed top-0 bottom-0 left-0 right-0 z-20 w-full h-full bg-black/80"
        onClick={onClose}
        role="none"
      ></div>
      <div className="fixed z-20 transform -translate-x-1/2 -translate-y-1/2 bg-white opacity-100 w-343px h-fit top-1/2 left-1/2 rounded-6px pt-26px pb-18px">
        {children}
      </div>
    </>
  );
}

export default Object.assign(PopUp, {
  PopUpMain,
  CenteredDescription,
  PopUpButton,
  DualMufflerImg,
  ModeSelectCard,
  ModelSelectCard,
  ModelCarousel,
  PopUpProgressBar,
});
