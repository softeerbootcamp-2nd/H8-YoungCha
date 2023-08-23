import { LinkProps, NavLink, useParams } from 'react-router-dom';
import { PathParamsType } from '@/types/router.ts';
import { padWithZero } from '@/utils';

interface ProgressBarProps {
  mode: 'self' | 'guide';
  id: string;
}

interface ProgressItemProps extends LinkProps {}

interface SelectedBarProps {
  active: number;
  length: number;
}

const PROGRESS_LIST = [
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '휠 선택',
  '외장 색상',
  '내장 색상',
  '옵션 선택',
  '견적 내기',
];

function ProgressBar() {
  const { step: _step, mode, id } = useParams() as PathParamsType;
  const step = Number(_step);

  return (
    <nav className="z-10 text-center min-w-768px title5 border-b-2px border-grey-002 px-32px">
      <div className="max-w-5xl mx-auto xl:px-96px xl:max-w-none">
        <ProgressList {...{ mode, id }} />
        <SelectedBar active={step - 1} length={PROGRESS_LIST.length} />
      </div>
    </nav>
  );
}

function ProgressList({ mode, id }: ProgressBarProps) {
  return (
    <div className="flex h-full whitespace-nowrap">
      {PROGRESS_LIST.map((item: string, index: number) => (
        <ProgressItem
          to={`/model/${id}/making/${mode}/${index + 1}`}
          key={`ProgressItem-${index}`}
        >
          {`${padWithZero(index + 1, 2)} ${item}`}
        </ProgressItem>
      ))}
    </div>
  );
}

function ProgressItem({ children, ...props }: ProgressItemProps) {
  return (
    <NavLink
      className={({ isActive }) =>
        `flex-1 flex justify-center items-center h-26px 
        ${isActive ? 'text-main-blue font-medium' : 'text-grey-002'}`
      }
      {...props}
    >
      {children}
    </NavLink>
  );
}

function SelectedBar({ active, length }: SelectedBarProps) {
  return (
    <span
      className="z-10 flex text-center duration-300 ease-in-out bg-main-blue h-2px -mb-2px"
      style={{
        width: `${100 / length}%`,
        transform: `translateX(${active * 100}%)`,
      }}
    />
  );
}

export default ProgressBar;
