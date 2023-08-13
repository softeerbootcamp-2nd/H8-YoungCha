import { TrimType } from '@/assets/mock/mock';
import MakingModeButton from '@/components/MakingModeButton';

interface TrimCardsBoxProps {
  trims: TrimType[];
}

function TrimCardsBox({ trims }: TrimCardsBoxProps) {
  return (
    <div className="flex justify-between gap-16px">
      <MakingModeButton
        name="Guide Mode"
        hashtag="#나만을 위한 팰리세이드"
        minPrice={38000000}
        description="나에게 딱 맞는 구성으로"
        to="making/guide/1"
      >
        <MakingModeButton.GuideModeDetailList />
      </MakingModeButton>
      {trims.map(
        ({ name, hashtag, minPrice, description, mainOptions }, index) => {
          return (
            <MakingModeButton
              name={name}
              hashtag={hashtag}
              minPrice={minPrice}
              description={description}
              to="making/self/1"
              key={`making-mode-button-${index}`}
            >
              <MakingModeButton.MainOptionList mainOptions={mainOptions} />
            </MakingModeButton>
          );
        }
      )}
    </div>
  );
}

export default TrimCardsBox;
