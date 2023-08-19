import { Fragment } from 'react';
import { TRIM_MAIN_OPTION } from '@/components/MakingModeButton/MainOptionList/constant';
import MainOption from '@/components/MakingModeButton/MainOptionList/MainOption';
import { TrimType } from '@/types';

interface MainOptionListProps extends Pick<TrimType, 'mainOptions'> {}

function MainOptionList({ mainOptions }: MainOptionListProps) {
  return (
    <>
      <h4 className="title5 pb-26px">{TRIM_MAIN_OPTION}</h4>
      <ul className="flex items-center gap-30px">
        {mainOptions.map((option, index) => (
          <Fragment key={`MainOption-${index}`}>
            {index !== 0 && <li className="bg-white w-1px h-40px"></li>}
            <MainOption {...option} />
          </Fragment>
        ))}
      </ul>
    </>
  );
}

export default MainOptionList;
