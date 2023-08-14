import type { Meta, StoryObj } from '@storybook/react';
import ProgressBar from './index';

const meta: Meta<typeof ProgressBar> = {
  component: ProgressBar,
};

export default meta;

type Story = StoryObj<typeof ProgressBar>;

export const FirstStory: Story = {
  args: { step: 1, path: 'self', id: 'LX06' },
};
