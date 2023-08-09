import OptionLayout from './OptionLayout';

interface DataType {
  imgUrl: string;
  description: string;
}
interface MainOptionBoxProps {
  mainOptionLists: DataType[][];
}

const TITLE = '핵심 옵션';

function MainOptionBox({ mainOptionLists }: MainOptionBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {mainOptionLists.map((option) => (
        <ul
          className="flex flex-col w-full gap-8px"
          key={option[0].description}
        >
          {option.map(({ imgUrl, description }) => (
            <li className="flex items-center gap-8px" key={description}>
              <img src={imgUrl} alt="description" className="w-80px h-60px" />
              <p className="body3 text-grey-004">{description}</p>
            </li>
          ))}
        </ul>
      ))}
    </OptionLayout>
  );
}

export default MainOptionBox;
