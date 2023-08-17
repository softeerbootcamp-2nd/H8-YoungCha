import * as Icon from '../../assets/icons';

const TEXT_PALISADE_KOR = '팰리세이드';

function ModelChangeButton() {
  return (
    <button className="flex items-center text-center title5">
      <Icon.CarIcon />
      <span className="w-70px">{TEXT_PALISADE_KOR}</span>
      <Icon.ModelChangeArrow />
    </button>
  );
}

export default ModelChangeButton;
