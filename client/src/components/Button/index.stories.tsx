import type { Meta, StoryObj } from '@storybook/react';
import Button from './index';

const meta: Meta<typeof Button> = {
  component: Button,
  argTypes: { onClick: { action: 'clicked' } },
};

export default meta;

type Story = StoryObj<typeof Button>;

export const Default: Story = {
  args: {
    children: 'Default',
    size: 'md',
    color: 'main-blue',
    onClick: () => {},
  },
};
export const Colors: Story = {
  render: () => (
    <>
      <Button size="md" color="main-blue">
        main-blue
      </Button>
      <Button size="md" color="white">
        white
      </Button>
      <Button size="md" color="grey">
        grey
      </Button>
    </>
  ),
};
export const Sizes: Story = {
  render: () => (
    <>
      <Button size="xl" color="main-blue">
        xl
      </Button>
      <Button size="lg" color="main-blue">
        lg
      </Button>
      <Button size="md" color="main-blue">
        md
      </Button>
      <Button size="sm" color="main-blue">
        sm
      </Button>
      <Button size="xs" color="main-blue">
        xs
      </Button>
    </>
  ),
};
