<script>
	import { fetchCreateFlashcard } from "$lib/api/flashcards";
	import { createdFlashcard, createdFlashcardInManagement } from "$lib/stores/flashcardStore";
  import { deckManagementOverlay, flashcardTypesOverlay, newFlashcardInDeckInterfaceOverlay, newFlashcardOverlay } from "$lib/stores/overlayStore.svelte";
  import { X, Trash2, Check, Plus, Circle } from '@lucide/svelte';
	import FlashcardTypesOverlay from "./FlashcardTypesOverlay.svelte";

  let { deckId } = $props();
   
  let flashcardType = $state('STANDARD_FLASHCARD');
  let flashcardFront = $state("");
  let flashcardBack = $state("")
  let question = $state("");
  let expectedAnswer = $state("");
  let answerList = $state(["", "", "", ""]);
  let answerIndicesList = $state(new Set());
  let selectedIndex = $state(0);
  let isRight = $state(true);
  let trueFalseList = $state([false, false, false, false]);

  const flashcardTypeAssociation = {
    'STANDARD_FLASHCARD': 'Standard Flashcard',
    'DISCURSIVE_QUESTION': 'Discursive Question',
    'MULTIPLE_ANSWERS_QUESTION': 'Multiple Answers',
    'MULTIPLE_CHOICE_QUESTION': 'Multiple Choice',
    'RIGHT_WRONG_QUESTION': 'Right or Wrong',
    'TRUE_FALSE_QUESTION': 'True or False'
  }

  const standardFlashcard = {
    type: 'STANDARD_FLASHCARD', 
    front: '',
    back: '',
    flashcardType: 'STANDARD_FLASHCARD'
  }

  const discursiveQuestion = {
    type: 'DISCURSIVE_QUESTION', 
    question: '',
    exampleCorrectAnswer: '',
    flashcardType: 'DISCURSIVE_QUESTION'
  }

  const multipleAnswersQuestion = {
    type: 'MULTIPLE_ANSWERS_QUESTION', 
    question: '',
    answerOptions: [],
    correctAnswerIndices: [],
    flashcardType: 'MULTIPLE_ANSWERS_QUESTION'
  }

  const multipleChoiceQuestion = {
    type: 'MULTIPLE_CHOICE_QUESTION', 
    question: '',
    answerOptions: [],
    correctAnswerIndex: 0,
    flashcardType: 'MULTIPLE_CHOICE_QUESTION'
  }

  const rightWrongQuestion = {
    type: 'RIGHT_WRONG_QUESTION', 
    question: '',
    isRight: true,
    flashcardType: 'RIGHT_WRONG_QUESTION'
  }

  const trueFalseQuestion = {
    type: 'TRUE_FALSE_QUESTION', 
    question: '',
    statements: [],
    trueFalseAnswers: [],
    flashcardType: 'TRUE_FALSE_QUESTION'
  }

  const token = localStorage.getItem("token");

  function changeType(newType) {
    flashcardType = newType;
    resetAll();
  }

  function resetAll() {
    flashcardType.set("STANDARD_FLASHCARD");
    flashcardFront.set("");
    flashcardBack.set("");
    question.set("");
    expectedAnswer.set("");
    answerList.set(["", "", "", ""]);
    answerIndicesList.set(new Set());
    selectedIndex.set(0);
    isRight.set(true);
    trueFalseList = [false, false, false, false];
  }

  async function createFlashcard() {
    let data = {};
    if(flashcardType == "STANDARD_FLASHCARD") {
      standardFlashcard.front = flashcardFront;
      standardFlashcard.back = flashcardBack;
      data = standardFlashcard;
    } else if(flashcardType == "DISCURSIVE_QUESTION") {
      discursiveQuestion.question = question;
      discursiveQuestion.exampleCorrectAnswer = expectedAnswer;
      data = discursiveQuestion;
    } else if(flashcardType == "MULTIPLE_ANSWERS_QUESTION") {
      multipleAnswersQuestion.question = question;
      multipleAnswersQuestion.answerOptions = answerList;
      multipleAnswersQuestion.correctAnswerIndices = Array.from(answerIndicesList);
      data = multipleAnswersQuestion;
    } else if(flashcardType == "MULTIPLE_CHOICE_QUESTION") {
      multipleChoiceQuestion.question = question;
      multipleChoiceQuestion.answerOptions = answerList;
      multipleChoiceQuestion.correctAnswerIndex = selectedIndex;
      data = multipleChoiceQuestion;
    } else if(flashcardType == "RIGHT_WRONG_QUESTION") {
      rightWrongQuestion.question = question;
      rightWrongQuestion.isRight = isRight;
      data = RightWrongQuestion;
    } else if(flashcardType == "TRUE_FALSE_QUESTION") {
      trueFalseQuestion.question = question;
      trueFalseQuestion.statements = answerList;
      trueFalseQuestion.trueFalseAnswers = trueFalseList;
      data = TrueFalseQuestion;
    } 

    try {
      await fetchCreateFlashcard(data, deckId, token);
      if($deckManagementOverlay !== null) createdFlashcardInManagement.set(true);
      createdFlashcard.set(true);
      newFlashcardOverlay.set(false);
      newFlashcardInDeckInterfaceOverlay.set(null);
    } catch(e) {
      alert(`Error: ${e.message}`);
    }

    resetAll();
  }
</script>

<div class="fixed inset-0 bg-black/80 flex justify-center items-center z-100">
  <div class="relative bg-white w-full max-w-3xl my-1 p-6 rounded-lg shadow-lg mx-4 max-h-[95vh] overflow-y-auto">
    <button onclick={() => {newFlashcardOverlay.set(false); newFlashcardInDeckInterfaceOverlay.set(null);}} class="absolute top-3 right-3 text-gray-500 hover:text-black transition-colors cursor-pointer" >
      <X size={16} />
    </button>
    <div class="flex justify-between items-center mb-2 mt-2">
      <h2 class="text-xl font-bold text-(--color14)">Create new flashcard</h2>
      <button data-user-button onclick={() => flashcardTypesOverlay.update(open => !open)} class="relative transition-colors ring-offset-background px-3 bg-white border border-input rounded-md whitespace-nowrap gap-2 justify-center items-center h-10 cursor-pointer border-(--color13) hover:bg-(--color8)">
        <p class="font-medium text-sm text-(--color14)">{flashcardTypeAssociation[flashcardType]}</p>
        <!-- <ArrowUpDown size={16} color="var(--color14)" /> -->
        {#if $flashcardTypesOverlay}
          <FlashcardTypesOverlay 
            changeType={(flashcardType) => changeType(flashcardType)}
          />
        {/if}
      </button>
    </div>
    {#if flashcardType == "STANDARD_FLASHCARD"}
    <div>
      <label for="front" class="text-sm font-semibold">Flashcard Front</label>
      <input id="front" placeholder="Front" bind:value={flashcardFront} class="mt-1 mb-4 w-full h-10 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required/>
    </div>
    <div>
      <label for="back" class="text-sm font-semibold">Flashcard Back</label>
      <input id="back" placeholder="Back" bind:value={flashcardBack} class="mt-1 mb-6 w-full h-10 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" />
    </div>
    {:else if flashcardType == "DISCURSIVE_QUESTION"}
    <div>
      <label for="discursive-question" class="text-sm font-semibold">Question</label>
      <textarea id="discursive-question" placeholder="Type the question ..." bind:value={question} class="mt-1 mb-4 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    <div>
      <label for="discursive-expected-answer" class="text-sm font-semibold">Expected Answer</label>
      <textarea id="discursive-expected-answer" placeholder="Type the expected answer ..." bind:value={expectedAnswer} class="mt-1 mb-6 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    {:else if flashcardType == "MULTIPLE_ANSWERS_QUESTION"}
    <div>
      <label for="multiple-answers-question" class="text-sm font-semibold">Question</label>
      <textarea id="multiple-answers-question" placeholder="Type the question ..." bind:value={question} class="mt-1 mb-4 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    <div for="multiple-answers-options" class="mb-3 text-sm font-medium leading-none">Answer Options</div>
    <div class="space-y-4 mt-1 mb-6">
      {#each answerList as answer, index}
        <div class="flex gap-2 items-center">
          {#if answerIndicesList.has(index)}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => {
                answerIndicesList.delete(index); 
                answerIndicesList = new Set(answerIndicesList);
              }} 
              class=" flex bg-green-500 items-center justify-center h-4 w-4 shrink-0 rounded-sm cursor-pointer">
              <Check size={16} color=white/>
            </button>
          {:else}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => {
                answerIndicesList.add(index);
                answerIndicesList = new Set(answerIndicesList);
              }} 
              class="h-4 w-4 shrink-0 rounded-sm border border-black cursor-pointer ">
            </button>
          {/if}
          <input id="option-{index+1}" placeholder="Option {index+1}" bind:value={answerList[index]} class="w-full h-10 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" />
          <button
            class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors h-10 w-10 bg-red-500 border border-red-500"
            onclick={() => {
              answerIndicesList.delete(index);
              let temp = new Set();
              answerIndicesList.forEach(value => {
                if(value > index) {
                  answerIndicesList.delete(value);
                  temp.add(value-1);
                } else {
                  temp.add(value);
                }
              });
              answerIndicesList = new Set(temp);
              answerList.splice(index, 1);
            }}>
            <Trash2 size={16} color=white/>
          </button>
        </div>
      {/each}
      <!-- svelte-ignore a11y_consider_explicit_label -->
      <button 
        class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors border border-(--color13) hover:bg-(--accent) h-10 px-4 py-2 w-full cursor-pointer"
        onclick={() => answerList.push("")}
        >
        <Plus size={16} class="color-(--color14)" />
        Add Option
      </button>
      <p class="text-sm text-gray-600">Select all correct options</p>
    </div>
    {:else if flashcardType == "MULTIPLE_CHOICE_QUESTION"}
    <div>
      <label for="multiple-choice-question" class="text-sm font-semibold">Question</label>
      <textarea id="multiple-choice-question" placeholder="Type the question ..." bind:value={question} class="mt-1 mb-4 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    <div for="multiple-choice-options" class="mb-3 text-sm font-medium leading-none">Answer Options</div>
    <div class="space-y-4 mt-1 mb-6">
      {#each answerList as answer, index}
        <div class="flex gap-2 items-center">
          {#if selectedIndex === index}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => selectedIndex = index} 
              class="flex h-4 w-4 items-center justify-center shrink-0 rounded-full cursor-pointer border border-black">
              <Circle size={16} color="black" fill="black" />
            </button>
          {:else}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => selectedIndex = index} 
              class="h-4 w-4 shrink-0 rounded-full border border-black cursor-pointer ">
            </button>
          {/if}
          <input id="option-choice-{index+1}" placeholder="Option {index+1}" bind:value={answerList[index]} class="w-full h-10 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" />
          <button
            class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors h-10 w-10 bg-red-500 border border-red-500"
            onclick={() => {
              answerList.splice(index, 1);
              if(selectedIndex>0) selectedIndex--;
            }}>
            <Trash2 size={16} color=white/>
          </button>
        </div>
      {/each}
      <!-- svelte-ignore a11y_consider_explicit_label -->
      <button 
        class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors border border-(--color13) hover:bg-(--accent) h-10 px-4 py-2 w-full cursor-pointer"
        onclick={() => answerList.push("")}
        >
        <Plus size={16} class="color-(--color14)" />
        Add Option
      </button>
      <p class="text-sm text-gray-600">Select the correct option</p>
    </div>
    {:else if flashcardType == "RIGHT_WRONG_QUESTION"}
    <div>
      <label for="right-wrong-question" class="text-sm font-semibold">Question</label>
      <textarea id="right-wrong-question" placeholder="Type the question ..." bind:value={question} class="mt-1 mb-4 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    <div class="mb-3 text-sm font-medium leading-none">Answer</div>
    <div id="right" class="flex gap-2 items-center my-2">
      {#if isRight}
        <!-- svelte-ignore a11y_consider_explicit_label -->
        <button 
          onclick={() => isRight = true} 
          class="flex h-4 w-4 items-center justify-center shrink-0 rounded-full cursor-pointer border border-black">
          <Circle size={16} color="black" fill="black" />
        </button>
      {:else}
        <!-- svelte-ignore a11y_consider_explicit_label -->
        <button 
          onclick={() => isRight = true} 
          class="h-4 w-4 shrink-0 rounded-full border border-black cursor-pointer ">
        </button>
      {/if}
      <label for="right" class="text-sm font-medium leading-none">Right</label>
    </div>
    <div id="wrong" class="flex gap-2 items-center my-2">
      {#if !isRight}
        <!-- svelte-ignore a11y_consider_explicit_label -->
        <button 
          onclick={() => isRight = false} 
          class="flex h-4 w-4 items-center justify-center shrink-0 rounded-full cursor-pointer border border-black">
          <Circle size={16} color="black" fill="black" />
        </button>
      {:else}
        <!-- svelte-ignore a11y_consider_explicit_label -->
        <button 
          onclick={() => isRight = false} 
          class="h-4 w-4 shrink-0 rounded-full border border-black cursor-pointer ">
        </button>
      {/if}
      <label for="wrong" class="text-sm font-medium leading-none">Wrong</label>
    </div>
    <p class="text-sm text-gray-600">Mark the alternative as right or wrong.</p>
    {:else if flashcardType == "TRUE_FALSE_QUESTION"}
    <div>
      <label for="true-false-question" class="text-sm font-semibold">Question</label>
      <textarea id="true-false-question" placeholder="Type the question ..." bind:value={question} class="mt-1 mb-4 w-full h-25 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" required></textarea>
    </div>
    <div class="mb-3 text-sm font-medium leading-none">Answer Options</div>
    <div class="space-y-4 mt-1 mb-6">
      {#each answerList as answer, index}
        <div class="flex gap-2 items-center">
          {#if trueFalseList[index]}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => {
                trueFalseList[index] = false;
              }} 
              class=" flex bg-green-500 items-center justify-center h-4 w-4 shrink-0 rounded-sm cursor-pointer">
              <Check size={16} color=white/>
            </button>
          {:else}
            <!-- svelte-ignore a11y_consider_explicit_label -->
            <button 
              onclick={() => {
                trueFalseList[index] = true;
              }} 
              class="h-4 w-4 shrink-0 rounded-sm border border-black cursor-pointer ">
            </button>
          {/if}
          <input id="option-{index+1}" placeholder="Option {index+1}" bind:value={answerList[index]} class="w-full h-10 rounded-md border border-(--color13) text-(--color14) px-3 py-2 focus:outline-2 focus:outline-offset-2 focus:outline-black-500 text-base font-medium" />
          <button
            class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors h-10 w-10 bg-red-500 border border-red-500"
            onclick={() => {
              trueFalseList.splice(index,1);
              answerList.splice(index, 1);
            }}>
            <Trash2 size={16} color=white/>
          </button>
        </div>
      {/each}
      <!-- svelte-ignore a11y_consider_explicit_label -->
      <button 
        class="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors border border-(--color13) hover:bg-(--accent) h-10 px-4 py-2 w-full cursor-pointer"
        onclick={() => answerList.push("")}
        >
        <Plus size={16} class="color-(--color14)" />
        Add Option
      </button>
      <p class="text-sm text-gray-600">Select all true options</p>
    </div>
    {/if}
    <div class="flex justify-end gap-3">
      <button onclick={() => {newFlashcardOverlay.set(false); newFlashcardInDeckInterfaceOverlay.set(null);}} class="bg-white text-red-500 outline outline-offset-[-1px] outline-red-500 px-3 py-2 rounded transition-colors duration-250 hover:bg-[var(--color12)] hover:cursor-pointer">Cancel</button>
      <button onclick={createFlashcard} class="bg-(--color1) text-white px-4 py-2 rounded transition-colors duration-250 hover:bg-[var(--color6)] hover:cursor-pointer">Create</button>
    </div>
  </div>
</div>