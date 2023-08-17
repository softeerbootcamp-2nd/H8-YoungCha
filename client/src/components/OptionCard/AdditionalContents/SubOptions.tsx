import { Fragment, useState } from 'react';

function SubOptions() {
  const options = [
    '전방 충돌 방지 보조',
    '내비게이션 기반 스마트 크루즈 컨트롤',
    '고속도로 주행보조 2',
  ];
  const [isSubSelected, setIsSubSelected] = useState(0);

  function handleOnClick(
    event: React.MouseEvent<HTMLParagraphElement>,
    index: number
  ) {
    event.stopPropagation();
    setIsSubSelected(index);
  }

  return (
    <div className="flex flex-wrap text-grey-003 body3 mb-8px">
      {options.map((item, index) => (
        <Fragment key={`SubOption-${index}`}>
          <p
            className={`${
              isSubSelected === index ? 'text-grey-black font-medium' : ''
            }`}
            onClick={(event) => handleOnClick(event, index)}
            role="none"
          >
            {item}
          </p>
          {index !== options.length - 1 && <p>ㆍ</p>}
        </Fragment>
      ))}
    </div>
  );
}

export default SubOptions;
