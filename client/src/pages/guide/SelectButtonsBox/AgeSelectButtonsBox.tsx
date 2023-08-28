import ActiveCheck from '@/assets/icons/SelectCheck';
import InactiveCheck from '@/assets/icons/SelectCheckInactive';
import SelectButton from '@/components/SelectButton';
import { AgeType } from '../type';
import { Fragment, useState } from 'react';
import { useTagSelectContext } from '@/store/useTagSelectContext';

const ageSelectArray: Array<AgeType> = [
  '20대',
  '30대',
  '40대',
  '50대',
  '60대',
  '70대',
];

function AgeSelectButtonsBox() {
  const [hoveredAge, setHoveredAge] = useState<AgeType | ''>('');
  const { selectedAge, setSelectedAge } = useTagSelectContext();

  function handleOnclick(age: AgeType) {
    setSelectedAge(age);
  }

  return (
    <div className={`flex flex-col w-343px gap-12px justify-center`}>
      {ageSelectArray.map((age: AgeType) => {
        const isHovered = hoveredAge === age;
        const isSelected = selectedAge === age;

        return (
          <Fragment key={age}>
            <SelectButton
              type={
                isSelected ? 'iconActive' : isHovered ? 'iconActive' : 'default'
              }
              size={'lg'}
              onMouseEnter={() => setHoveredAge(age)}
              onMouseLeave={() => setHoveredAge('')}
              onClick={() => handleOnclick(age)}
            >
              {age}
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

export default AgeSelectButtonsBox;
