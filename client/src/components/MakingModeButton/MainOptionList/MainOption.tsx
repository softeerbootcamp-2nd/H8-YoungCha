import { MainOptionType } from '@/assets/mock/mock.ts';

interface MainOptionProps extends MainOptionType {}

function MainOption({ imgUrl, description }: MainOptionProps) {
  return (
    <li className="flex flex-col items-center gap-10px">
      <img src={imgUrl} alt={description} className="w-60px h-40px"></img>
      <div className="text-center whitespace-pre-line body3">{description}</div>
    </li>
  );
}

export default MainOption;
