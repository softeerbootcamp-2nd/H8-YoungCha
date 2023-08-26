import { useModal } from '@/hooks/useModal';
import ModelChangePopUp from './HeaderPopUp/ModelChangePopUp';
import * as Icon from '@/assets/icons';

const TEXT_PALISADE_KOR = '팰리세이드';

function ModelChangeButton() {
  const { isOpen, openPopUp, closePopUp } = useModal();

  return (
    <>
      <button
        className="flex items-center text-center title5"
        onClick={openPopUp}
      >
        <Icon.CarIcon />
        <span className="w-70px">{TEXT_PALISADE_KOR}</span>
        <Icon.ModelChangeArrow />
      </button>
      {isOpen && <ModelChangePopUp closePopUp={closePopUp} />}
    </>
  );
}

export default ModelChangeButton;
