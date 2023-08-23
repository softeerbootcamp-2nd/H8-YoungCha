function getStep(categoryId: number) {
  switch (categoryId) {
    case 4:
      return 5;
    case 5:
      return 6;
    case 6:
      return 4;
    default:
      return categoryId;
  }
}

export default getStep;
