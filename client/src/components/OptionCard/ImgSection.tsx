import { useOptionCardState } from '@/store/useOptionCardContext';
import { PowerTrainDetails } from '@/assets/mock/optionMock';

interface ImgSectionProps extends Pick<PowerTrainDetails, 'imgUrl'> {
  imgUrl: string;
  step: number;
}

function ImgSection({ imgUrl, step }: ImgSectionProps) {
  const { isActive } = useOptionCardState();
  let imgElement = <></>;
  switch (step) {
    case 4: //휠 선택
      imgElement = (
        <div
          className={`${
            isActive ? '' : 'opacity-40'
          } absolute top-20px left-265px`}
        >
          <img src={imgUrl} alt="N Performance" />
        </div>
      );
      break;
    case 5: //외장 색상
      imgElement = (
        <div className="absolute bg-white rounded-full top-50px left-285px w-60px h-60px border-1px border-grey-002"></div>
      );
      break;
    case 6: //내장 색상
      imgElement = (
        <img
          src={imgUrl}
          alt="내장 색상"
          className="absolute top-50px rounded-6px w-150px h-50px left-200px"
        />
      );
      break;
    case 7: //옵션 선택
      imgElement = (
        <div
          className={`${
            isActive ? '' : 'opacity-40'
          } absolute top-20px left-265px`}
        >
          <img src={imgUrl} alt="H Genuine Accessories" />
        </div>
      );
      break;
    default:
      imgElement = <></>;
  }

  return <div>{imgElement}</div>;
}

export default ImgSection;
