import type { Meta, StoryObj } from '@storybook/react';
import ProgressBar from './index';

const meta: Meta<typeof ProgressBar> = {
  component: ProgressBar,
};

export default meta;

type Story = StoryObj<typeof ProgressBar>;

export const FirstStory: Story = {
  args: { step: 1, mode: 'self', id: 'LX06' },
};
