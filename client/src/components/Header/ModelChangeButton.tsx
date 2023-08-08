import * as Icon from '../../assets/icons';

function ModelChangeButton() {
  return (
    <button className="flex text-center title5">
      <Icon.CarIcon />
      <span className="w-70px">팰리세이드</span>
      <Icon.ModelChangeArrow />
    </button>
  );
}

export default ModelChangeButton;
