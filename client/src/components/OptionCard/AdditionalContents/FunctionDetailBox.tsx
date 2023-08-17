import { PowerTrain } from '@/assets/mock/optionMock';
import { useOptionCardState } from '@/store/useOptionCardContext';

interface FunctionDetailBoxProps extends Pick<PowerTrain, 'details'> {}

function FunctionDetailBox({ details }: FunctionDetailBoxProps) {
  const { isActive } = useOptionCardState();
  return (
    <div
      className={`${
        isActive ? 'bg-[#f3f3f3]' : 'bg-grey-001'
      }  rounded-6px pt-4px pb-12px mt-12px`}
    >
      {details[0].specs.map((item, index) => (
        <div
          key={`FunctionDetail-${index}`}
          className={`${
            isActive ? 'text-[#4B4B4B]' : 'text-grey-003'
          } body3 mt-8px`}
        >
          <span className="p-12px">{item.name}</span>
          <span>{item.description}</span>
        </div>
      ))}
    </div>
  );
}

export default FunctionDetailBox;
