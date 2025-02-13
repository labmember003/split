import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.falcon.split.Presentation.getAppTypography
import com.falcon.split.Presentation.screens.AnimationComponents.UpwardFlipHeaderImage
import com.falcon.split.data.network.models_app.Group
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import split.composeapp.generated.resources.GroupPic
import split.composeapp.generated.resources.HomePic
import split.composeapp.generated.resources.Res
import split.composeapp.generated.resources.group_icon_filled
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    onCreateGroupClick: () -> Unit,
    onGroupClick: (Group) -> Unit,
    groups: List<Group>,
    isLoading: Boolean = false,
    navControllerMain: NavHostController
) {

    val lazyState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navControllerMain.navigate("create_group") },
                containerColor = Color(0xFF8fcb39)
            ) {
                Icon(Icons.Default.Add, "Add Expense")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (groups.isEmpty()) {
                EmptyGroupsView(
                    onCreateGroupClick = onCreateGroupClick,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    state = lazyState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item{
                        Box(){
//                            Image(
//                                painter = painterResource(Res.drawable.GroupPic), // Replace with your image resource
//                                contentDescription = "Home illustration",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(250.dp)
//                                    .padding(0.dp),
//                                contentScale = ContentScale.Crop
//                            )
                            UpwardFlipHeaderImage(
                                Res.drawable.GroupPic,
                                lazyState
                            )

//                            Card(
//                                shape = RoundedCornerShape(5.dp),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = Color.White
//                                ),
//                                elevation = CardDefaults.cardElevation(10.dp),
//                                modifier = Modifier
//                                    .padding(10.dp)
//                                    .wrapContentSize()
//                                    .padding(5.dp)
//                            ) {}


                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Number of Groups",
                                    style = getAppTypography().titleMedium,
                                    color = Color(0xFF64748B)
                                )
                                Text(
                                    text = "${groups.size}",
                                    style = getAppTypography().titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E293B)
                                )
                            }

                        }
                    }
                    items(groups) { group ->
                        GroupCard(
                            group = group,
                            onClick = { onGroupClick(group) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupCard(
    group: Group,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
            .padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.group_icon_filled),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(horizontal = 12.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Column {
                        Text(
                            text = group.name,
                            style = getAppTypography().titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${group.members.size} members",
                            style = getAppTypography().titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onClick) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "View Group",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyGroupsView(
    onCreateGroupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.group_icon_filled),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 12.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No Groups Yet",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Create a group to start splitting expenses with friends",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilledTonalButton(
            onClick = onCreateGroupClick
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Create Group")
        }
    }
}

@Composable
fun GroupsScreenWithDummyData(
    onCreateGroupClick: () -> Unit,
    onGroupClick: (Group) -> Unit,
    navControllerMain: NavHostController
) {
    // Dummy groups data
    val dummyGroups = remember {
        listOf(
            Group(
                id = "1",
                name = "Weekend Trip to Goa",
                members = emptyList(),
                createdBy = "user1",
                createdAt = null
            ),
            Group(
                id = "2",
                name = "House Expenses",
                members = emptyList(),
                createdBy = "user2",
                createdAt = null
            )
        )
    }

    // Using dummy loading state
    var isLoading by remember { mutableStateOf(false) }

    // Simulate loading when screen first appears
    LaunchedEffect(Unit) {
        isLoading = true
        delay(1000) // Simulate network delay
        isLoading = false
    }

    GroupsScreen(
        groups = dummyGroups,
        isLoading = isLoading,
        onCreateGroupClick = onCreateGroupClick,
        onGroupClick = onGroupClick,
        navControllerMain = navControllerMain
    )
}
