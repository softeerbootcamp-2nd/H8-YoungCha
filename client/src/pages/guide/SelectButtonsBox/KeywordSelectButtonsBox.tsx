import { useState } from 'react';
import KeywordButton from './KeywordButton';

const keywordSelectArray = [
  {
    title: '내 차는 이런 부분에서 강했으면 좋겠어요',
    keywords: ['주행력', '소음', '효율', '파워'],
  },
  {
    title: '나는 차를 탈 때 이런게 중요해요',
    keywords: ['디자인', '차량 보호', '온도 조절', '건강', '신기술', '안전'],
  },
  {
    title: '나는 차를 이렇게 활용하고 싶어요',
    keywords: ['차박', '가족 여행'],
  },
];
function KeywordSelectButtonsBox() {
  const [selectedKeyword, setSelectedKeyword] = useState<Array<string>>([]);
  const [hoveredKeyword, setHoveredKeyword] = useState<string>('');

  function handleClickKeyword(keyword: string) {
    const keywordIndex = selectedKeyword.indexOf(keyword);
    if (keywordIndex === -1) {
      if (selectedKeyword.length === 3) return;

      setSelectedKeyword([...selectedKeyword, keyword]);
    } else {
      setSelectedKeyword([
        ...selectedKeyword.slice(0, keywordIndex),
        ...selectedKeyword.slice(keywordIndex + 1),
      ]);
    }
  }

  return (
    <div className={`flex flex-col w-343px gap-32px mt-21px`}>
      {keywordSelectArray.map(({ title, keywords }) => {
        return (
          <div key={title} className="flex flex-col gap-16px">
            <h3 className="text-grey-black title4">{title}</h3>
            <div className="grid grid-cols-2 gap-12px">
              {keywords.map((keyword) => (
                <KeywordButton
                  key={keyword}
                  keyword={keyword}
                  selectedKeyword={selectedKeyword}
                  hoveredKeyword={hoveredKeyword}
                  handleClickKeyword={handleClickKeyword}
                  setHoveredKeyword={setHoveredKeyword}
                />
              ))}
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default KeywordSelectButtonsBox;
