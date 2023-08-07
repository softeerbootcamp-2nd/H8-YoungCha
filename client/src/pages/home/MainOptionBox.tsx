import React from 'react';
import OptionLayout from './OptionLayout';

type DataType = { imgUrl: string; description: string };
interface MainOptionBoxProps {
  datas: DataType[][];
}

const TITLE = '핵심 옵션';

function MainOptionBox({ datas }: MainOptionBoxProps) {
  return (
    <OptionLayout title={TITLE}>
      {datas.map((data) => (
        <ul className="flex flex-col w-full gap-8px" key={data[0].description}>
          {data.map(({ imgUrl, description }) => (
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
