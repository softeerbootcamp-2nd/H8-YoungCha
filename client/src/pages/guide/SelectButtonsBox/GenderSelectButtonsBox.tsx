import ActiveCheck from '@/assets/icons/select-check';
import InactiveCheck from '@/assets/icons/select-check-inactive';
import SelectButton from '@/components/SelectButton';
import useTagRefreshNavigator from '@/hooks/useTagRefreshNavigator';
import { GenderType } from '../type';
import { Fragment, useState } from 'react';
import { useTagSelectContext } from '@/store/useTagSelectContext';

const genderSelectArray: Array<GenderType> = ['남성', '여성', '선택 안함'];

function GenderSelectButtonsBox() {
  const [hoveredGender, setHoveredGender] = useState<GenderType | ''>('');
  const { selectedGender, setSelectedGender } = useTagSelectContext();

  useTagRefreshNavigator();
  function handleOnclick(Gender: GenderType) {
    setSelectedGender(Gender);
  }

  return (
    <div className={`flex flex-col w-343px gap-12px mt-173px`}>
      {genderSelectArray.map((Gender: GenderType) => {
        const isHovered = hoveredGender === Gender;
        const isSelected = selectedGender === Gender;

        return (
          <Fragment key={Gender}>
            <SelectButton
              type={
                isSelected ? 'iconActive' : isHovered ? 'iconActive' : 'default'
              }
              size={'lg'}
              onMouseEnter={() => setHoveredGender(Gender)}
              onMouseLeave={() => setHoveredGender('')}
              onClick={() => handleOnclick(Gender)}
            >
              {Gender}
              {isSelected ? (
                <ActiveCheck />
              ) : isHovered ? (
                <ActiveCheck />
              ) : (
                <InactiveCheck />
              )}
            </SelectButton>
          </Fragment>
        );
      })}
    </div>
  );
}

export default GenderSelectButtonsBox;
