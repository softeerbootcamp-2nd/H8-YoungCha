import type { Meta, StoryObj } from '@storybook/react';
import PopUp from './index';
import {
  ChangeToGasolinePopUp,
  ModelChangePopUp,
  ModeChangePopUp,
  SelfModeCard,
  GuideModeCard,
  ModelListPopUpText,
} from './constant';

const meta: Meta<typeof PopUp> = {
  component: PopUp,
};

export default meta;

type Story = StoryObj<typeof PopUp>;

function closePopUp(): void {
  throw new Error('Function not implemented.');
}

export const ModelChange: Story = {
  args: {
    children: (
      <PopUp onClose={closePopUp}>
        <PopUp.PopUpMain
          title={ModelChangePopUp.title}
          imgSrc={<ModelChangePopUp.ImgSrc />}
        />
        <PopUp.CenteredDescription description={ModelChangePopUp.description} />
        <PopUp.PopUpButton
          greyButtonContent={ModelChangePopUp.greyButtonContent}
          blueButtonContent={ModelChangePopUp.blueButtonContent}
        />
      </PopUp>
    ),
  },
};

export const PowerTrainChange: Story = {
  args: {
    children: (
      <PopUp onClose={closePopUp}>
        <PopUp.PopUpMain
          title={ChangeToGasolinePopUp.title}
          imgSrc={<ChangeToGasolinePopUp.ImgSrc />}
        />
        <PopUp.CenteredDescription
          description={ChangeToGasolinePopUp.description}
        />
        <PopUp.DualMufflerImg />
        <PopUp.PopUpButton
          greyButtonContent={ChangeToGasolinePopUp.greyButtonContent}
          blueButtonContent={ChangeToGasolinePopUp.blueButtonContent}
        />
      </PopUp>
    ),
  },
};

export const ModeChangeToGuide: Story = {
  args: {
    children: (
      <PopUp onClose={closePopUp}>
        <PopUp.PopUpMain
          title={ModeChangePopUp.title}
          imgSrc={<ModeChangePopUp.ImgSrc />}
        />
        <PopUp.ModeSelectCard
          currentMode="self"
          mode={SelfModeCard.mode}
          description={SelfModeCard.description}
        />
        <PopUp.ModeSelectCard
          currentMode="self"
          mode={GuideModeCard.mode}
          description={GuideModeCard.description}
        />
      </PopUp>
    ),
  },
};

export const ModeChangeToSelf: Story = {
  args: {
    children: (
      <PopUp onClose={closePopUp}>
        <PopUp.PopUpMain
          title={ModeChangePopUp.title}
          imgSrc={<ModeChangePopUp.ImgSrc />}
        />
        <PopUp.ModeSelectCard
          currentMode="guide"
          mode={SelfModeCard.mode}
          description={SelfModeCard.description}
        />
        <PopUp.ModeSelectCard
          currentMode="guide"
          mode={GuideModeCard.mode}
          description={GuideModeCard.description}
        />
      </PopUp>
    ),
  },
};

export const ModelList: Story = {
  args: {
    children: (
      <PopUp onClose={closePopUp}>
        <PopUp.PopUpMain
          title={ModelChangePopUp.title}
          imgSrc={<ModelChangePopUp.ImgSrc />}
        />
        <PopUp.CenteredDescription
          description={ModelListPopUpText.description}
        />
        <PopUp.PopUpProgressBar />
        <PopUp.ModelCarousel />
        <PopUp.PopUpButton
          greyButtonContent={ModelListPopUpText.greyButtonContent}
          blueButtonContent={ModelListPopUpText.blueButtonContent}
        />
      </PopUp>
    ),
  },
};
