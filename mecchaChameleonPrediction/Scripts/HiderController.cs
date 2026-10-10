using UnityEngine;

// 숨는 사람 입력 처리: 칠하기 / 스포이트 / 자세 고정 (예상 구현)
public class HiderController : MonoBehaviour
{
    [SerializeField] private Camera cam;
    [SerializeField] private BodyPainter painter;
    [SerializeField] private Eyedropper eyedropper;
    [SerializeField] private ColorPalette palette;
    [SerializeField] private Animator animator;

    private bool frozen;

    private void Update()
    {
        if (GameManager.Instance.Phase == GamePhase.Result) return;

        // 페인트 단계에는 자기 몸을 보면서 칠하고, 숨기 단계에도 덧칠 가능하다고 가정
        if (Input.GetMouseButton(0))
            painter.TryPaint(cam.ScreenPointToRay(Input.mousePosition), palette.Current);

        if (Input.GetMouseButtonDown(1))
            eyedropper.Pick();

        // 숨바꼭질 핵심: 자세를 잡고 완전히 멈추기 (움직이면 바로 들킴)
        if (Input.GetKeyDown(KeyCode.F))
        {
            frozen = !frozen;
            animator.speed = frozen ? 0f : 1f;
            GetComponent<CharacterMovement>().enabled = !frozen;
        }
    }
}
