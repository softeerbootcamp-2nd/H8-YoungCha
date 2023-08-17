import { useOptionCardState } from '@/store/useOptionCardContext';

function SubOptionDescription() {
  const { isActive } = useOptionCardState();
  const description =
    '선행 차량이 갑자기 속도를 줄이거나, 앞에 정지 차량 혹은 보행자가 나타나는 등 전방 충돌 위험이 감지되면 경고를 해줍니다. 경고 후에도 충돌 위험이 높아지면 자동으로 제동을 도와줍니다.';
  return (
    <div
      className={`${
        isActive ? 'text-grey-black' : 'text-grey-003'
      } body3 mt-8px`}
    >
      {description}
    </div>
  );
}

export default SubOptionDescription;
