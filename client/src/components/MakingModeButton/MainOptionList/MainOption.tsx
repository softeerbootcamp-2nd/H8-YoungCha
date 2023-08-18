import { MainOptionType } from '@/assets/mock/mock.ts';
import { removeBracket } from '@/utils';

interface MainOptionProps extends MainOptionType {}

function MainOption({ imgUrl, name }: MainOptionProps) {
  return (
    <li className="flex flex-col items-center gap-10px w-70px">
      <img
        src={imgUrl}
        alt={name}
        className="w-60px h-40px"
        style={{
          filter: 'brightness(10)',
        }}
      ></img>
      <div className="text-center whitespace-pre-line body3">
        {removeBracket(name)}
      </div>
    </li>
  );
}

export default MainOption;
