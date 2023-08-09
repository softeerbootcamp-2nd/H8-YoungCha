import type { Meta, StoryObj } from '@storybook/react';
import Button from './index';

const meta: Meta<typeof Button> = {
  component: Button,
  argTypes: { onClick: { action: 'clicked' } },
};

export default meta;

type Story = StoryObj<typeof Button>;

export const StorySize: Story = {
  args: {
    children: 'StorySize',
    size: 'sm',
    color: 'main-blue',
    onClick: () => {},
  },
};

export const StoryColor: Story = {
  args: {
    children: 'StoryColor',
    size: 'sm',
    color: 'grey',
    onClick: () => {},
  },
};
