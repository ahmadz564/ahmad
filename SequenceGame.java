public class SequenceGame extends AppCompatActivity {

    private TextView sequenceTextView;
    private Button startButton;
    private ArrayList<Integer> sequenceList;
    private int sequenceLength;
    private int currentStep;
    private boolean isPlaying;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_game);

        sequenceTextView = findViewById(R.id.sequenceTextView);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    startGame();
                }
            }
        });
    }

    private void startGame() {
        isPlaying = true;
        startButton.setText(R.string.playing_text);
        sequenceList = new ArrayList<>();
        sequenceLength = 1;
        currentStep = 0;
        showSequence();
    }

    private void showSequence() {
        sequenceTextView.setText("");
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int randomNumber = random.nextInt(9);
                sequenceList.add(randomNumber);
                sequenceTextView.append(String.valueOf(randomNumber));
                currentStep++;
                if (currentStep < sequenceLength) {
                    sequenceTextView.append(",");
                    showSequence();
                } else {
                    currentStep = 0;
                    getUserInput();
                }
            }
        }, 1000);
    }

    private void getUserInput() {
        sequenceTextView.setText("");
        sequenceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    int userInput = Integer.parseInt(((TextView) v).getText().toString());
                    if (userInput == sequenceList.get(currentStep)) {
                        currentStep++;
                        if (currentStep == sequenceLength) {
                            sequenceLength++;
                            currentStep = 0;
                            showSequence();
                        }
                    } else {
                        endGame();
                    }
                }
            }
        });
    }

    private void endGame() {
        isPlaying = false;
        startButton.setText(R.string.start_text);
        sequenceTextView.setText(R.string.game_over_text);
    }
}
