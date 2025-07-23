<script>
  import { flashcardTypesOverlay } from '$lib/stores/overlayStore.svelte';
  import { clickOutside } from '$lib/actions/clickOutside';

  let { changeType } = $props();

  function changeTypeOverlay(newType) {
    flashcardTypesOverlay.set(false);
    changeType(newType);
  }

  const flashcardTypes = [
    {
      typeTitle: "Standard Flashcard",
      flashcardType: "STANDARD_FLASHCARD"
    },
    {
      typeTitle: "Discursive Question",
      flashcardType: "DISCURSIVE_QUESTION"
    },
    {
      typeTitle: "Multiple Answers",
      flashcardType: "MULTIPLE_ANSWERS_QUESTION"
    },
    {
      typeTitle: "Multiple Choice",
      flashcardType: "MULTIPLE_CHOICE_QUESTION"
    },
    {
      typeTitle: "Right or Wrong",
      flashcardType: "RIGHT_WRONG_QUESTION"
    },
    {
      typeTitle: "True or False",
      flashcardType: "TRUE_FALSE_QUESTION"
    }
  ];

</script>
{#snippet flashcardTypeButton({typeTitle, flashcardType})}
  <button onclick={() => changeTypeOverlay(flashcardType)} class="text-start px-2 py-1.5 text-sm text-(--color14) w-full hover:bg-gray-100 hover:cursor-pointer">{typeTitle}</button>
{/snippet}

<div use:clickOutside={() => flashcardTypesOverlay.set(false)} class="absolute flex flex-col items-start right-0 top-10 z-50 bg-white p-1 mt-1 min-w-55 max-h-[150px] overflow-y-auto rounded-md shadow border border-(--color13)">
  {#each flashcardTypes as type}
    {@render flashcardTypeButton(type)}
  {/each}
</div>